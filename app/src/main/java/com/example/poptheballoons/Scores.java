/*
 * Written by Shurong Tian for CS6326.001, assignment 6, starting 11/7/19.
 * NetID: sxt151030
 *
 * This class is an object class that has getters and setters to store data
 * */

package com.example.poptheballoons;


public class Scores {
    private String name;
    private String score;
    private String datetime;

    public Scores(String name, String score, String datetime) {
        this.name = name;
        this.score = score;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}





