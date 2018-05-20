package com.example.admin.bubblemaths;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class SocialMediaAuth extends AppCompatActivity {
    WebView webView;
    Twitter twitter = TwitterFactory.getSingleton();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_auth);
        webView = findViewById(R.id.authWebView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url){
//                System.out.println(url);
                if (url.startsWith("http://stansolutions.net")){
                    Uri uri = Uri.parse(url);
                    final String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                    if (oauthVerifier != null){
                        Log.i("Twitter Status","authenticated");
                        BackgroundThread.run(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);
                                    twitter.setOAuthAccessToken(accessToken);

                                    Intent intent = new Intent();
                                    intent.putExtra("access token", accessToken.getToken());
                                    intent.putExtra("access token secret", accessToken.getTokenSecret());

                                    setResult(RESULT_OK, intent);
                                    finish();
                                }catch (Exception e) {
                                    Log.i("Twitter Error", e.toString());



                                }
                            }
                        });
                    }
                }
                super.onLoadResource(view, url);
            }
        });

        BackgroundThread.run(new Runnable() {
            @Override
            public void run() {
                try {

                    RequestToken requestToken = twitter.getOAuthRequestToken();
                    final String requestUrl = requestToken.getAuthenticationURL();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl(requestUrl);
                        }
                    });
                } catch (IllegalStateException e){
//                    If an access token already exits, continue on, so we don't hang. This isn't the most elegant solution, but it works.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("http://stansolutions.net");
                        }
                    });
                } catch (Exception e) {
                    Log.i("Twitter Error", e.toString());

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }

}
