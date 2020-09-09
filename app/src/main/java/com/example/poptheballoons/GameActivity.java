/**************************************************************************
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This is the activity where the GameView is displayed. It also contains the
 * randomlized condition, the countdown timer and the score.
 * **************************************************************************/

package com.example.poptheballoons;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    String color[] = {"red", "orange", "yellow", "green", "blue", "purple", "white"};
    String shape[] = {"circle", "square"};

    private static int score;
    private static TextView textViewScore;
    private static TextView textViewTimer;
    private static final long START_TIME = 60000;
    public static int count = 60;
    public boolean exit;
    private View gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //generate random color and shape for the condition
        Random r = new Random();
        int rColor = r.nextInt((6 - 0) + 1) + 0;
        int rShape = r.nextInt((1 - 0) + 1) + 0;

        //give a condition to the user
        TextView textViewCondition = (TextView) findViewById(R.id.textViewCondition);
        String condition = "Pop the " + color[rColor] + " " + shape[rShape] + "s";
        textViewCondition.setText(condition);

        score = 0;
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        String scoreText = "Score: " + score;
        textViewScore.setText(scoreText);

        textViewTimer = (TextView) findViewById(R.id.textViewTimer);

        GameView.setSelectShape(rShape);
        GameView.setSelectColor(rColor);


        //create thread for count down timer
        Thread t = new Thread(){
            @Override
            public void run(){
                while (!exit){
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(count > 0){
                                //display countdown
                                textViewTimer.setText("Time: " + count);
                                count--;}
                                else{
                                    stop1();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //when time is up, go to the EnterScore page
                startActivity(new Intent(GameActivity.this, EnterScore.class));
            }
        };
        t.start();
    }

    /**************************************************************************
     * gives a flag for the tread to stop.
     **************************************************************************/
    public void stop1()
    {
        exit = true;
    }

    /**************************************************************************
     * method to increment score for each right balloon poped
     **************************************************************************/
    public static void incrementScore() {
        score++;
        if(score % 10 == 0){
            count += 10;
        }

        String scoreText = "Score: " + score;
        textViewScore.setText(scoreText);
    }

    /**************************************************************************
     * method to decrement score for each wrong balloon poped
     **************************************************************************/
    public static void decrementScore() {
        score--;
        String scoreText = "Score: " + score;
        textViewScore.setText(scoreText);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameActivity.score = score;
    }

    public static void setCount(int count) {
        GameActivity.count = count;
    }
}
