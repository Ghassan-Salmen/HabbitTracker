package com.example.habbittracker.model;

public class HistoryModel {

    private int increase;
    private String time;
    private String date;
    private String habit;
    private int id;
    private int duration;
    private int score;

    public HistoryModel() {
    }

    public HistoryModel(int increase, String time, String date, String habit, int id, int duration, int score) {
        this.increase = increase;
        this.time = time;
        this.date = date;
        this.habit = habit;
        this.id = id;
        this.duration = duration;
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
