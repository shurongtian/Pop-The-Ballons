/**************************************************************************
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * Pop The Balloon Android Game
 *
 * This program creates a simple Android Game.
 * The game has four screens (activities) - Start screen with instruction,
 * play screen with balloons rising from the bottom and going off the top,
 * when the time is up, a screen is shown for the user to enter the name for
 * a new score, then the user sees a screen with all high scores
 *
 *
 * This class is a superclass for object balloon upon creation, its shape,
 * size, color and position is randomlized.
 **************************************************************************/

package com.example.poptheballoons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import java.util.Random;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;
import static android.graphics.Color.BLUE;


public class Balloon {

    private int xLocation;
    private int yLocation;
    private int size;
    private int color;
    private int num;
    private int randomX;
    private int speed;
    private int viewWidth;
    private int viewHeight;
    private int rColor;
    private int[] colors = {RED, Color.parseColor("#FF8C00"), YELLOW, GREEN, BLUE, Color.parseColor("#BA55D3"), WHITE};
    private boolean shape;
    private int left;
    private int right;
    private int top;
    private int bottom;


    /**************************************************************************
     * Balloon constructor
     **************************************************************************/
Balloon(Context context){
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    viewWidth = size.x;
    viewHeight = size.y;



    this.speed = getRandomSpeed();
    this.yLocation = viewHeight;
    this.size = getRandomSize();
    this.num = getRandomNum();
    this.xLocation = getRandomX(viewWidth);
    this.color = getRandomColor(colors);
    this.shape = getRandomShape();


}

    /**************************************************************************
     * a random boolean to generate circle and square randomly
     **************************************************************************/
public boolean getRandomShape(){
    Random random = new Random();
    boolean rShape = random.nextBoolean();
    return rShape;
}

    /**************************************************************************
     * generate a random number for the color of each balloon
     **************************************************************************/
public int getRandomColor(int[] colors){
    Random r = new Random();
    rColor = r.nextInt((6 - 0) + 1) + 0;
    return colors[rColor];
}

    /**************************************************************************
     * generate a random number for the number of the balloons
     **************************************************************************/
public int getRandomNum(){
    Random r = new Random();
    num = r.nextInt((12 - 6) + 1) + 6;
    return num;
}
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean getShape() {
        return shape;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    /**************************************************************************
     * generate a random number for the size of each balloon
     **************************************************************************/
    public int getRandomSize(){
        Random r = new Random();
        size = r.nextInt((96 - 48) +1) + 48;
        return size;
    }

    /**************************************************************************
     * generate a random number for the speed of each balloon
     **************************************************************************/
    public int getRandomSpeed(){
        Random r = new Random();
        speed = r.nextInt((20 - 5) +1)+5;
        return speed;
    }

    /**************************************************************************
     * generate a random number for the position of each balloon
     **************************************************************************/
    public int getRandomX(int viewWidth){
    Random r = new Random();
    randomX = r.nextInt(((viewWidth - size - 100) - 70) + 1) + 70;
    return randomX;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    /**************************************************************************
     * draw balloons with random color, shape and size
     **************************************************************************/
    void drawBalloon(Canvas canvas){

        viewWidth = canvas.getWidth();
        viewHeight = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(color);

        //randomly draw squares and circles
        //1->square    0->circle
        if(shape){
            canvas.drawRect(xLocation, yLocation, xLocation + size, yLocation + size, paint);
            yLocation -= speed;

            left = xLocation;
            right = xLocation + size;
            top = yLocation;
            bottom = yLocation + size;
        }
            else{
            canvas.drawCircle(xLocation, yLocation,  size / 2, paint);
            yLocation -= speed;

            left = xLocation - size/2;
            right = xLocation + size/2;
            top = yLocation - size/2;
            bottom = yLocation + size/2;
        }
    }

    /**************************************************************************
     * check if a balloon contains a pixel
     **************************************************************************/
    boolean contains(int x, int y){
        if(x <= right && x >= left) {
            if(y <= bottom && y >= top)
                return true;
        }
        return false;
    }
}
