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

    public static Context contextOfApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        createWidjListeners();
        getPreferenceValues();
        contextOfApplication = getApplicationContext();
        String socialMediaPref = sharedPreferences.getString("socialMediaPref",null);
        if (socialMediaPref == null){
            socialMediaPref = "None";
            sharedPreferences.edit().putString("socialMediaPref",socialMediaPref).apply();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        switch (buttonText){
            case "Settings":
                Intent settingsIntent = new Intent(this,SettingsActivity.class );
                startActivity(settingsIntent);
                break;
            case "Play Game":
                Intent gameIntent = new Intent(this,GameActivity.class );
                startActivity(gameIntent);
                break;
            case "High Scores":
                Intent highScoresIntent = new Intent(this,HighScores.class );
                startActivity(highScoresIntent);



        }

    }

// #TODO Add comments to eveything and try and group methods logically if you can be arsed.

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

