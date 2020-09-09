/*
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This class is for activity_enterscore, it shows all the scores
 * from the text file and display as a listview.
 * this class also sends the user to activity_enterscore when the
 * user presses the add button
 * */
package com.example.poptheballoons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HighScore extends AppCompatActivity {

    private Button addButton;
    private Button buttonNewGame;
    ListView listView;
    FileIO fileIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        this.listView = (ListView) findViewById(R.id.ListView);
        //fileIO = new FileIO(this);

        tryLV();

        //click on add button brings up EnterScoreAct activity
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.setScore(0);
                startActivity(new Intent(HighScore.this, EnterScore.class));
            }
        });

        buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.setCount(60);
                startActivity(new Intent(HighScore.this, GameActivity.class));
            }
        });
    }

    /**********************************************************************
     * This method reads the text file into a string array list and
     * convert it into a object Scores array list to pass into
     * the adapter for display as a list view
     * It also does the sorting
     **********************************************************************/
    public void tryLV(){
        fileIO = new FileIO(this);

        ArrayList<String> fileData = new ArrayList<>(fileIO.readFile());
        ArrayList<Scores> scoreList = new ArrayList<>();

        for(String item: fileData){
            String[] items = item.split("\t");

            for (int i = 0; i < items.length; i++) {
                Scores scr = new Scores(items[i], items[i + 1], items[i + 2]);
                scoreList.add(scr);
                i += 2;
            }
        }


        //sort the list
        Collections.sort(scoreList, new Comparator<Scores>() {
            @Override
            public int compare(Scores s1, Scores s2) {
                //compare the scores
                int compareHelperResult = compareHelper(s1, s2);
                //if the scores are the same, compare the dates
                if (compareHelperResult == 0) {
                    return compareHelperByDate(s1, s2);
                }
                else {
                    return compareHelperResult;
                }
            }
            //helper to compare scores
             private int compareHelper(Scores s1, Scores s2) {
                //convert string scores to integers
                Integer score1 = new Integer(s1.getScore());
                Integer score2 = new Integer(s2.getScore());
                return score1.compareTo(score2);
            }
            private int compareHelperByDate(Scores s1, Scores s2) {
                Date date1 = new Date(s1.getDatetime());
                Date date2 = new Date(s2.getDatetime());
                return date1.compareTo(date2);
            }
        });

        //reverse the order of the list after sorted the score
        Collections.reverse(scoreList);

        //adapt to the listview for display
        StableArrayAdapter adapter = new StableArrayAdapter(this,R.layout.listview_adapter, scoreList);
        this.listView.setAdapter(adapter);
    }


}
