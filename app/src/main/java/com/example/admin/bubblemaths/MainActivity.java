package com.example.admin.bubblemaths;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private SharedPreferences sharedPreferences;
    public static Context contextOfApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        createWidjListeners();
        getPreferenceValues();
        contextOfApplication = getApplicationContext();
        String socialMediaPref = sharedPreferences.getString("socialMediaPref",null);
        Log.i("Test", "The value of social media pref at oncreate() is " + socialMediaPref);
        if (socialMediaPref == null){
            Log.i("Test", "Setting the value of social media to None");
            socialMediaPref = "None";
            sharedPreferences.edit().putString("socialMediaPref",socialMediaPref).apply();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Intent intent = new Intent(this,SettingsActivity.class );
            startActivity(intent);
            return true;
        }else if (id == R.id.highScores){
//            #TODO High scores activity
            Intent highScoresIntent = new Intent(this,HighScores.class );
            startActivity(highScoresIntent);
            Log.i("Test", "High Scores");

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        switch (buttonText){
            case "Settings":
                Intent settingsIntent = new Intent(this,SettingsActivity.class );
                startActivity(settingsIntent);
                Log.i("Test", "Settings");
                break;
            case "Play Game":
                Intent gameIntent = new Intent(this,GameActivity.class );
                startActivity(gameIntent);
//                 Intent to navifate to play game and fire intent #TODO Play game activity and intent
                Log.i("Test", "Play Game");
                break;
            case "High Scores":
                Intent highScoresIntent = new Intent(this,HighScores.class );
                startActivity(highScoresIntent);
                Log.i("Test", "High Scores");


//#TODO Make a high scores activity  and action an intent + make a button on landing page, make an icon for activity
        }

    }



    public void createWidjListeners(){
//        Handles the creation of all the buttons.
        Button playGameButton = findViewById(R.id.playGameButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        Button highScoresButton = findViewById(R.id.highscoreButton);
        playGameButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        highScoresButton.setOnClickListener(this);
    }

    public void getPreferenceValues(){

    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }



}

