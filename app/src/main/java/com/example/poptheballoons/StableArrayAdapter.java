/*
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This class adapts the data from the text file into a list view
 * */

package com.example.poptheballoons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StableArrayAdapter extends ArrayAdapter<Scores> {
    private Context mContext;
    int mResource;

    public StableArrayAdapter (Context context, int resource, ArrayList<Scores> objects){
        super (context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get info
        String name = getItem(position).getName();
        String score = getItem(position).getScore();
        String datetime = getItem(position).getDatetime();

        Scores scores = new Scores(name, score, datetime);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView lvName = (TextView) convertView.findViewById(R.id.lvName);
        TextView lvScore = (TextView) convertView.findViewById(R.id.lvScore);
        TextView lvDateTime = (TextView) convertView.findViewById(R.id.lvDateTime);

        lvName.setText(name);
        lvScore.setText(score);
        lvDateTime.setText(datetime);

        return convertView;
    }
}
