/*
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This class parse entered data, pass them into file through FileIO class when
 * the user press add button.
 * This class also checks for validation of user inputs
 * */
package com.example.poptheballoons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnterScore extends AppCompatActivity {

    private Button saveButton;

    EditText newName, newScore, newDateTime;

    TextView TextViewMissed;

    String name, score, dateTime, str;

    SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );

    FileIO fileIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterscore);

        setTitle("NEW SCORE");
        fileIO = new FileIO(this);

        //generate current date/time for autopopulated date/time
        Date currentDate = new Date();
        str = sdf.format(currentDate);

        //getting data from editTexts
        newName = (EditText) findViewById(R.id.newName);
        newScore = (EditText) findViewById(R.id.newScore);
        newDateTime = (EditText) findViewById(R.id.newDateTime);
        TextViewMissed = (TextView) findViewById(R.id.textViewMissed);

        //populate current time
        newDateTime.setText(str);
        newScore.setText("" + GameActivity.getScore());
        TextViewMissed.setText("You missed " + GameView.getCountMissingBalloon() + " balloons!");

        //click on save button
        saveButton= findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                name = newName.getText().toString();
                score = newScore.getText().toString();
                dateTime = newDateTime.getText().toString();

                if (validation(name, score, dateTime)) {

                    ArrayList<String> scoreData = new ArrayList<>(fileIO.readFile());
                    scoreData.add(name + "\t" + score + "\t" + dateTime + "\t");
                    fileIO.writeFile(scoreData);

                    startActivity(new Intent(EnterScore.this, HighScore.class));
                }
            }
        });


    }

    /**********************************************************************
     * check if entered info is validated
     **********************************************************************/

    private boolean validation(String name, String score, String datetime){

        //passing a flag for validation
        boolean isValid = true;

        //name cannot be empty or more than 30 char
        if(name.isEmpty()) {
            newName.setError("Name cannot be empty");
            isValid = false;
        }

        else if(name.length() > 30){
            newName.setError("Name cannot be more than 30 characters");
            isValid = false;
        }


        //score can only be positive int
        if(score.isEmpty()) {
            newScore.setError("Score cannot be empty");
            isValid = false;
        }

        else if(Integer.parseInt(score) < 0 ){
            newScore.setError("Score cannot be negative");
            isValid = false;
        }

        //date/time format
        if(datetime.isEmpty()) {
            newDateTime.setError("Date/Time cannot be empty");
            isValid = false;
        }

        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateFormat.setLenient(false);

            try {
                Date newDate = dateFormat.parse(datetime.trim());
            } catch (ParseException e) {
                newDateTime.setError("Format is not valid");
                e.printStackTrace();
                isValid = false;
            }
        }
        return isValid;
    }
}
