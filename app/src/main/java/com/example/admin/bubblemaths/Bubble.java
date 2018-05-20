package com.example.admin.bubblemaths;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Bubble {
    private Paint paint;
    private Paint textColour;
    private int[] colors = new int[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA};
    private float x,y;
    private float xDir,yDir;
    private static Random random = new Random();
    static int bubbleRadius = 200;
    String bubbleFlag;
    float bubbleSpeed = 0.01f;
    private Question question = new Question();
    private String fakeAnswerString;
    private String answerString;
    Bubble(String bubbleFlag){
        x = y = 100;
        float xFloat = random.nextFloat();
        float yFloat = random.nextFloat();
        xDir = xFloat * 10 + bubbleSpeed;
        yDir = yFloat * 10 + bubbleSpeed;
        this.bubbleFlag = bubbleFlag;
        paint = new Paint();
        paint.setColor(getRandColor());
        textColour = new Paint();
        textColour.setColor(Color.WHITE);
        textColour.setTextAlign(Paint.Align.CENTER);
        textColour.setTextSize(100);
        fakeAnswerString = question.getFakeAnswerString();
        answerString = question.getAnswerString();

    }



    void move() {

        x += xDir;
        y += yDir;
    }

    void bounce(float width, float height) {
        if ((xDir > 0 && x > width) || (xDir < 0 && x < 0)) {
            xDir = -1 * xDir;
        }

        if ((yDir > 0 && y > height) || (yDir < 0 && y < 0)) {
            yDir = -1 * yDir;
        }
    }
    void drawAnswerBubble(Canvas canvas){
        canvas.drawCircle(x,y, bubbleRadius, paint);
        canvas.drawText(answerString,x,y,textColour );
    }

    void draw(Canvas canvas) {
        canvas.drawCircle(x,y, bubbleRadius, paint);
        canvas.drawText(fakeAnswerString,x,y,textColour );
    }

    private int getRandColor(){
        int randomNum = random.nextInt(colors.length);
        return colors[randomNum] ;
    }
    @Override
    public String toString(){
        return this.x + "," + this.y;
    }
}

