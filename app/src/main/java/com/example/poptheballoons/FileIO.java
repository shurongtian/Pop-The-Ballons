/*
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This class is for read from file and write to file
 * */

package com.example.poptheballoons;


import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileIO {
    private Context context;
    private File file;
    private String fileName = "Scores.txt";

    public FileIO(Context cxt){
        this.context = cxt;
    }


    /**********************************************************************
     * this method read from the text file , scans the whole file and stores
     * it into a string array list
     **********************************************************************/
    public ArrayList<String> readFile(){

        ArrayList<String> list = new ArrayList<>();

        try {
            this.file = new File(this.context.getFilesDir(), this.fileName);

            Scanner s = new Scanner(this.file);
            while(s.hasNextLine()){
                list.add(s.nextLine());
            }
            s.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**********************************************************************
     * this method writes the data into a text file
     **********************************************************************/
    public void writeFile(ArrayList<String> list){

        this.file = new File(this.context.getFilesDir(),this.fileName);
        //if the text file does not exist, create one
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {

            FileWriter fw = new FileWriter(file);
            for(String s: list){
                fw.write(s);
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
