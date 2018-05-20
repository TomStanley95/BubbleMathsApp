package com.example.admin.bubblemaths;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class Display  extends View{
    private List<Bubble> bubbles;

    public Display(Context context, @Nullable AttributeSet attrs){super(context,attrs);}
    GameActivity gameActivity = (GameActivity) this.getContext();

    void setModel(List<Bubble> bubbles) {

        this.bubbles = bubbles;
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (bubbles == null){
            return;
        }
        for (Bubble bubble : bubbles){
            if (bubble.bubbleFlag.equals("normal")){
                bubble.draw(canvas);
            }else {
                bubble.drawAnswerBubble(canvas);
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        double errorMarginForTouch = 1.5 * Bubble.bubbleRadius;
//        Gets the x and y co ordinates of the touch event
        float xTouch = event.getX();
        float yTouch = event.getY();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
//            Loops through every bubble comparing it's x and y to the touch event
                for (Bubble bubble : bubbles){
                    String bubbleCoOrds = bubble.toString();
                    String[] listBubbleCoOrds = bubbleCoOrds.split(",");
                    float bubbleX = Float.parseFloat(listBubbleCoOrds[0]);
                    float bubbleY = Float.parseFloat(listBubbleCoOrds[1]);
//                  If we've pressed near a bubble accounting for a margin of error
                    if ( xTouch >= (bubbleX - errorMarginForTouch) && xTouch <= (bubbleX + errorMarginForTouch) && yTouch >= (bubbleY - errorMarginForTouch) && yTouch <= (bubbleY + errorMarginForTouch) ){
//                        Test to see if its the answer bubble
                        if (bubble.bubbleFlag.equals("answer")){
//                        If it is the correct bubble, next question.
                            gameActivity.nextQuestion();

                        }
                    }

                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        super.onTouchEvent(event);
        return true;
    }
}
