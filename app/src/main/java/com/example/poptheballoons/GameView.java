/**************************************************************************
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This is a view class that contains the GameView. GameView contains a canvas
 * where all of the balloons are drawn. The class also contains a Touch Event
 * which allow the user to pop the balloons. This class also has a method to prevent
 * the balloons from overlapping each other.
 * **************************************************************************/

package com.example.poptheballoons;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class GameView extends View {

    private ArrayList<Balloon> balloons = new ArrayList<Balloon>();
    private int randNum;

    private Runnable r = new Runnable() {
        @Override
        public void run()
        {
            invalidate();

        }
    };

    private Handler h = new Handler();
    private int frameRate = 60;
    private Context gameContext;
    OnCustomEventListener mListener;
    private static int selectShape;
    private static int selectColor;
    private static int countMissingBalloon = 0;

    public interface OnCustomEventListener {
        public void onEvent();
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        mListener = eventListener;
    }

    public static void setSelectShape(int shape) {
        selectShape = shape;
    }

    public static void setSelectColor(int color) {
        selectColor = color;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas){

        if(balloons.size() == 0) {

            Random r = new Random();

            //generate a random number for balloons and put this number
            //of balloons into the array list
            randNum = r.nextInt((20 - 10) + 1) + 10;
            for(int i = 0; i < randNum; i++) {
                this.balloons.add(new Balloon(gameContext));
            }
        }
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        //draw the balloons
        for(int i = 0; i < balloons.size(); i++){

            this.balloons.get(i).drawBalloon(canvas);
        }

        Paint paint = new Paint();
        paint.setStrokeWidth(20);
        paint.setColor(Color.WHITE);

        //paint the borders of the canvas
        canvas.drawLine(0, 0, this.getWidth() - 1, 0,paint);
        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
                this.getHeight() - 1, paint);
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
                this.getHeight() - 1, paint);


        //collision
        for(int i = 0; i < this.balloons.size(); i++) {
            for(int j = 0; j < this.balloons.size(); j++) {
                if(i != j) {
                    if(isCollided(this.balloons.get(i), this.balloons.get(j))) {

                        if(this.balloons.get(i).getLeft() > this.balloons.get(j).getLeft()) {
                            this.balloons.get(i).setxLocation(this.balloons.get(i).getxLocation() + 5);
                            this.balloons.get(j).setxLocation(this.balloons.get(j).getxLocation() - 5);
                        } else {
                            this.balloons.get(j).setxLocation(this.balloons.get(j).getxLocation() + 5);
                            this.balloons.get(i).setxLocation(this.balloons.get(i).getxLocation() - 5);
                        }
                    }
                }
            }
        }
        //if the balloon goes out of the top of the view, remove it from the arraylist
        for(int i = 0; i < balloons.size(); i++){
            if (this.balloons.get(i).getBottom() <= 0){

                int tempColor = 0;
                int[] colors = {RED, Color.parseColor("#FF8C00"), YELLOW, GREEN, BLUE, Color.parseColor("#BA55D3"), WHITE};
                for(int j = 0; j < colors.length; j++) {
                    if(colors[j] == balloons.get(i).getColor())
                        tempColor = j;
                }
                int tempShape = balloons.get(i).getShape() ? 1 : 0;

                if(tempColor == selectColor && tempShape == selectShape) {
                    countMissingBalloon++;
                }

                balloons.remove(i);
                this.balloons.add(new Balloon(gameContext));
            }
        }
        h.postDelayed(r, frameRate/2);
    }

    //touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                for(int i = 0; i < balloons.size(); i++) {
                    if(balloons.get(i).contains(X, Y)){

                        int tempColor = 0;
                        int[] colors = {RED, Color.parseColor("#FF8C00"), YELLOW, GREEN, BLUE, Color.parseColor("#BA55D3"), WHITE};
                        for(int j = 0; j < colors.length; j++) {
                            if(colors[j] == balloons.get(i).getColor())
                                tempColor = j;
                        }
                        int tempShape = balloons.get(i).getShape() ? 1 : 0;

                        if(tempColor == selectColor && tempShape == selectShape) {
                            GameActivity.incrementScore();
                        } else {
                            GameActivity.decrementScore();
                        }
                        balloons.remove(i);
                        this.balloons.add(new Balloon(gameContext));
                    }
                }
                break;
        }
        return true;
    }


    /**************************************************************************
     * method to check if two balloons are collided by checking if a pixel of
     * a balloon is included in the other balloon
     **************************************************************************/
    public boolean isCollided (Balloon bal1, Balloon bal2){

        int left = Math.max(bal1.getLeft(), bal2.getLeft());
        int right = Math.min(bal1.getRight(), bal2.getRight());
        int top = Math.min(bal1.getTop(), bal2.getTop());
        int bottom = Math.max(bal1.getBottom(), bal2.getBottom());

        for (int x = left; x < right; x++) {
            for (int y = top; y < bottom; y++) {
                if (bal1.contains(x,y) && bal2.contains(x,y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getCountMissingBalloon() {
        return countMissingBalloon;
    }
}
