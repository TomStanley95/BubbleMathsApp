package com.example.admin.bubblemaths;

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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

//#TODO Make it so that if you access setting from game, the back button takes you back to game. - This is not a priority,Dont waste hours on it.
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Question question = new Question();
    private SharedPreferences sharedPreferences;
    private Display display;
    private List<Bubble> gameBubbles;
    int gameDifficulty = 1;
    int currentGameScore = 0;
    long startQuestionTime;
    long endQuestionTime;
    long questionTimeInSecs;


    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        switch (buttonText){
            case "End Game":
                Intent highScoresIntent = new Intent(this,HighScores.class );
                startActivity(highScoresIntent);
                Log.i("Test", "End Game Button pressed");
                break;
        }

    }

    private Thread drawingThread = new Thread(new Runnable() {

        private boolean running;

        @Override
        public void run() {
            try {
                running = true;
                while (running) {
                    Thread.sleep(10);


                    // tell the display to update itself - on the GUI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            display.invalidate();
                        }
                    });

                    // tell the model to update
                    for (Bubble bubble : gameBubbles) {
                        bubble.move();
                        bubble.bounce(display.getWidth(), display.getHeight());
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    });
    void calcGameScore(){
        String questionTimeString = Long.toString(questionTimeInSecs);
        currentGameScore += Integer.parseInt(questionTimeString) * gameDifficulty;
    }
    void nextQuestion(){
        getGameTime("End");
        calcQuestionTime();
        calcGameScore();
        gameDifficulty += 1;
        initQuestion();
        createBubbles();

    }
    void setQuestionTextViews(){
        TextView questionTextView = (TextView) findViewById(R.id.questionText);
        questionTextView.setText(question.getQuestion());
        TextView scoreTextView = (TextView) findViewById(R.id.gameScore);
        scoreTextView.setText(getStringGameScore());
    }
    String getStringGameScore(){
        return "Your current score is: " + Integer.toString(currentGameScore);
    }
    void initQuestion(){
        question.makeAnswerZero();
        question.buildQuestion();
        setQuestionTextViews();
        getGameTime("Start");
    }
    public void getGameTime(String flag){
        Calendar time = Calendar.getInstance();
        if (flag.equals("Start")){

            startQuestionTime = time.getTimeInMillis();
        }else{
            endQuestionTime = time.getTimeInMillis() ;
        }

    }

    void calcQuestionTime(){
        Long questionTimeInMs = endQuestionTime - startQuestionTime;
        questionTimeInSecs = TimeUnit.MILLISECONDS.toSeconds(questionTimeInMs);


    }
    void createBubbles(){
//        #TODO Figure out why creating a new list of bubbles increases their speed seemingly automatically
        int numberOfBubbles = gameDifficulty/2 * 5;
        gameBubbles = new ArrayList<>();
        gameBubbles = Collections.synchronizedList(gameBubbles);
        for (int i = 0; i < numberOfBubbles ; ++i){
            gameBubbles.add(new Bubble("normal"));
//            Log.i("Test", "A bubble is being added to  gameBubbles");
        }
        gameBubbles.add(new Bubble("answer"));
        display = findViewById(R.id.display);
        display.setModel(gameBubbles);
        drawingThread.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createWidjListeners();
        initQuestion();
        createBubbles();




    }

    public void createWidjListeners(){
//        Handles the creation of all the buttons + the clock.
        Button endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
            Log.i("Test", "High Scores");
        }

        return super.onOptionsItemSelected(item);
    }
}
