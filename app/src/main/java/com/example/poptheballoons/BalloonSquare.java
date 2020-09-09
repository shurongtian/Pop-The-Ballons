/**************************************************************************
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This is a subclass for Balloon where it gets the area of the balloon if
 * it is a square
 * **************************************************************************/
package com.example.poptheballoons;
import android.content.Context;

public class BalloonSquare extends Balloon {


    BalloonSquare(Context context) {
        super(context);
    }

    public int SquareArea(int size){
        int sArea = size * size;
        return sArea;
    }
}
