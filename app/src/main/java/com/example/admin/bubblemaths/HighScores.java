package com.example.admin.bubblemaths;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScores extends AppCompatActivity implements View.OnClickListener{
    public ScoresDAOHelper scoresDAOHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createWidjListeners();
        scoresDAOHelper = new ScoresDAOHelper(this);
        updateScore();
    }
//    #TODO This updates the score view, use this to update the list of scores on the highscores page, you will need to modify it.
    void updateScore(){
        Cursor cursor = scoresDAOHelper.getReadableDatabase().rawQuery("select * from scores", null);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("scores: ");
//        #TODO Instead of a while loop iterate through it and each each score to the list element. Make a post current score on social method. Gets the first score from the db.
        while (cursor.moveToNext()){
            stringBuilder.append(cursor.getString(1)).append(" ");
        }
        cursor.close();

//        TextView scoreView = (TextView) findViewById(R.id.scoreView);
//        scoreView.setText(stringBuilder.toString().trim());
    }
    void addScore(String score){
        SQLiteDatabase db = scoresDAOHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("value", score);
        db.insert("scores",null, contentValues);
        updateScore();
    }
    @Override
    protected void onDestroy(){
        scoresDAOHelper.close();
        super.onDestroy();
    }
// #TODO Have a button that authorizes and then posts to twitter or just posts if user already auth'd
public void createWidjListeners(){
//        Handles the creation of all the buttons.
    Button twitterButton = findViewById(R.id.twitterButton);
    twitterButton.setOnClickListener(this);
}
    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        Log.i("Test", Integer.toString(button.getId()));
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

        }

    }
}
