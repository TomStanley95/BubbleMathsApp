package com.example.admin.bubblemaths;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class HighScores extends AppCompatActivity implements View.OnClickListener{
    private static final int AUTHENTICATE = 1;
    public ScoresDAOHelper scoresDAOHelper;
    Twitter twitter = TwitterFactory.getSingleton();
    String currentScore;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createWidjListeners();
        scoresDAOHelper = new ScoresDAOHelper(this);
        intent = getIntent();
        currentScore = intent.getStringExtra("currentGameScore");
//        Check to see if the current score is empty  if it is, set it to zero don't add it.
        try{
            boolean getScoreIsEmpty = currentScore.isEmpty();
            System.out.println(getScoreIsEmpty);
        }catch (NullPointerException e){
            currentScore = "0";

        }
        if (!currentScore.equals("0")){
            addScore(currentScore);
        }
        updateScoreTextViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_high_scores, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this,SettingsActivity.class );
            startActivity(intent);
            return true;
        }else if (id == R.id.highScores){
            Intent highScoresIntent = new Intent(this,HighScores.class );
            startActivity(highScoresIntent);
        }else if (id == R.id.play){
            Intent playGameIntent = new Intent(this, GameActivity.class);
            startActivity(playGameIntent);
        }else if (id == R.id.home){
            Intent homeIntent = new Intent(this,MainActivity.class);
            startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    void updateScoreTextViews(){
        Cursor cursor = scoresDAOHelper.getReadableDatabase().rawQuery("select * from scores", null);
//        Most recent score should be the last score added into the database
        TextView mostRecentScore = findViewById(R.id.mostRecentScoreTextView);
        cursor.moveToLast();
        String recentScore = "Your most recent score is: " + cursor.getString(1);
        mostRecentScore.setText(recentScore);
        TextView previousScores = findViewById(R.id.previousScores);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your previous scores are:");
        stringBuilder.append("\n");
        while (cursor.moveToPrevious()){
            String stringToAddToBuilder = cursor.getString(1) + "\n";
            stringBuilder.append(stringToAddToBuilder);

        }

        previousScores.setText(stringBuilder.toString().trim());
        cursor.close();


    }
    void addScore(String score){
        SQLiteDatabase db = scoresDAOHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", score);
        db.insert("scores",null, contentValues);
        updateScoreTextViews();
    }
    @Override
    protected void onDestroy(){
        scoresDAOHelper.close();
        super.onDestroy();
    }

public void createWidjListeners(){
//        Handles the creation of all the buttons.
    Button twitterButton = findViewById(R.id.twitterButton);
    twitterButton.setOnClickListener(this);
}
    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        switch (buttonText){
            case "Post latest score on Twitter":
                authorise(pressedButton);

                break;

        }

    }
    public void authorise(View view){
        Intent intent = new Intent(this,SocialMediaAuth.class);
        startActivityForResult(intent, AUTHENTICATE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == AUTHENTICATE && resultCode == RESULT_OK){
            BackgroundThread.run(new Runnable() {
                @Override
                public void run() {
                    String token = data.getStringExtra("access token");
                    String secret = data.getStringExtra("access token secret");
                    AccessToken accessToken = new AccessToken(token, secret);

                    twitter.setOAuthAccessToken(accessToken);


                    try{
                        twitter.updateStatus("I just scored " + currentScore + " points in BubbleMaths");


                    } catch (final Exception e){
                        Log.i("Twitter Error", e.toString());

                    }

                }
            });
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String parentActivity = intent.getStringExtra("parentActivity");
        if (parentActivity == null){
            return super.onKeyDown(keyCode, event);
        }
        if (parentActivity.equals("GameActivity")){
            if (keyCode == KeyEvent.KEYCODE_BACK){
                Intent newGameIntent = new Intent(this,GameActivity.class);
                startActivity(newGameIntent);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
