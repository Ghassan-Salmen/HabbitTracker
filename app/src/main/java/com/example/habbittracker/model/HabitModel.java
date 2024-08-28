package com.example.habbittracker.model;

public class HabitModel {

    private int increase;
    private int id;
    private int score;
    private String habit;
    private int duration;

    public HabitModel(int increase, int score, String habit, int duration) {
        this.increase = increase;

        this.score = score;
        this.habit = habit;
        this.duration = duration;
    }

    public HabitModel() {
    }

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

