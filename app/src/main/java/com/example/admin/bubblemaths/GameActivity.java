package com.example.admin.bubblemaths;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Question question = new Question();
    private Display display;
    private List<Bubble> gameBubbles;
    int gameDifficulty = 1;
    int currentGameScore = 0;
    long startQuestionTime;
    long endQuestionTime;
    long questionTimeInSecs;
    MediaPlayer soundPlayer;


    @Override
    public void onClick(View pressedButton){
        Button button = (Button) pressedButton;
        String buttonText = button.getText().toString();
        switch (buttonText){
//            On click handling for the end game button, go to the high scores screen and pass along the current score
            case "End Game":
                Intent highScoresIntent = new Intent(this,HighScores.class );
                highScoresIntent.putExtra("parentActivity", "GameActivity");
                highScoresIntent.putExtra("currentGameScore", getStringGameScore());
                startActivity(highScoresIntent);
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
//        Get the time the question took in seconds, and multiply it by the game difficulty to get the score.
        String questionTimeString = Long.toString(questionTimeInSecs);
        currentGameScore += Integer.parseInt(questionTimeString) * gameDifficulty;
    }
    void nextQuestion(){
//        go through the process of creating a new question, records the end of the question time and calls the calcGameScore()
        getGameTime("End");
        calcQuestionTime();
        calcGameScore();
        gameDifficulty += 1;
        initQuestion();
        createBubbles();

    }
    void setQuestionTextViews(){
//        Sets the text view to the current question
        TextView questionTextView = findViewById(R.id.questionText);
        questionTextView.setText(question.getQuestion());
        TextView scoreTextView =  findViewById(R.id.gameScore);
        scoreTextView.setText(getStringGameScore());
    }
    String getStringGameScore(){
        return  Integer.toString(currentGameScore);
    }
    void initQuestion(){
//        resets the current answer to zero and builds a new question, records the start of the question
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
//        I have a pretty big bug here. I think it's something to do with the synchronizedList, however I couldn't find a way to fix it.
//        I would love it if you could let me know what the bug was when you grade it. Thanks in Advance - Tom
//        #TODO Figure out why creating a new list of bubbles increases their speed seemingly automatically
        int numberOfBubbles = gameDifficulty/2 * 5;
        gameBubbles = new ArrayList<>();
        gameBubbles = Collections.synchronizedList(gameBubbles);
        for (int i = 0; i < numberOfBubbles ; ++i){
            gameBubbles.add(new Bubble("normal"));
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
        createWidjListeners();
        initQuestion();
        createBubbles();

        soundPlayer = MediaPlayer.create(this, R.raw.game_sound);
        soundPlayer.setLooping(true);
        soundPlayer.start();



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
            Intent settingsIntent = new Intent(this,SettingsActivity.class );
            settingsIntent.putExtra("parentActivity", "GameActivity");
            startActivity(settingsIntent);


        }else if (id == R.id.highScores){
            Intent highScoresIntent = new Intent(this,HighScores.class );
            highScoresIntent.putExtra("parentActivity", "GameActivity");
            highScoresIntent.putExtra("currentGameScore", getStringGameScore());
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
    protected void onPause() {
        soundPlayer.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
