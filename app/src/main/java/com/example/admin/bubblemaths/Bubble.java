package com.example.admin.bubblemaths;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Bubble {
    private Paint paint;
    private Paint textColour;
//    simple array holding colours which will be randomly chosen.
    private int[] colors = new int[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA};
    private float x,y;
    private float xDir,yDir;
    private static Random random = new Random();
    static int bubbleRadius = 200;
//    bubbleFlag is used to draw a bubble with either a fake answer or the correct answer
    String bubbleFlag;
    private String fakeAnswerString;
    private String answerString;
    Bubble(String bubbleFlag){
        x = y = 100;
        float xFloat = random.nextFloat();
        float yFloat = random.nextFloat();
        float bubbleSpeed = 0.01f;
        xDir = xFloat * 10 + bubbleSpeed;
        yDir = yFloat * 10 + bubbleSpeed;
        this.bubbleFlag = bubbleFlag;
        paint = new Paint();
        paint.setColor(getRandColor());
        textColour = new Paint();
        textColour.setColor(Color.WHITE);
        textColour.setTextAlign(Paint.Align.CENTER);
        textColour.setTextSize(100);
        Question question = new Question();
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

