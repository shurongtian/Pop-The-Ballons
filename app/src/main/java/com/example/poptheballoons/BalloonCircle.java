/**************************************************************************
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This is a subclass for Balloon where it gets the area of the balloon if
 * it is a circle
 * **************************************************************************/
package com.example.poptheballoons;
import android.content.Context;

public class BalloonCircle extends Balloon {

    int diameter;


    BalloonCircle(Context context) {
        super(context);
    }

    public int circleArea(int size){
        double tempArea = 3.1415 * (size / 2) * (size / 2);
        double cArea = Math.ceil(tempArea);
        return (int)cArea;
    }
}
