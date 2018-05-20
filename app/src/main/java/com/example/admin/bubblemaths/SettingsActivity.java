package com.example.admin.bubblemaths;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
