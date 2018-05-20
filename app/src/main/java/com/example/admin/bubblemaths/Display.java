package com.example.admin.bubblemaths;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        boolean handled = false;
        double errorMarginForTouch = 1.5 * Bubble.bubbleRadius;
        float xTouch = event.getX();
        float yTouch = event.getY();
        int pointerId;
        int actionIndex = event.getActionIndex();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
//                Log.i("Test", "Down Press");
                for (Bubble bubble : bubbles){
                    String bubbleCoOrds = bubble.toString();
                    String[] listBubbleCoOrds = bubbleCoOrds.split(",");
                    float bubbleX = Float.parseFloat(listBubbleCoOrds[0]);
                    float bubbleY = Float.parseFloat(listBubbleCoOrds[1]);
//                  If we've pressed near a bubble accounting for a margin of error
                    if ( xTouch >= (bubbleX - errorMarginForTouch) && xTouch <= (bubbleX + errorMarginForTouch) && yTouch >= (bubbleY - errorMarginForTouch) && yTouch <= (bubbleY + errorMarginForTouch) ){
//                        Test to see if its the answer bubble
                        if (bubble.bubbleFlag.equals("answer")){
//                            Log.i("Test", "We dit it, answer bubble pressed");
                            gameActivity.nextQuestion();

                        }
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
//                Log.i("Test", "Press Released");
                break;
        }
//        for (Bubble bubble : bubbles){
//
//            Log.i("Test", bubble.toString());
//        }
        handled = true;
        return super.onTouchEvent(event) || handled;
    }
}
