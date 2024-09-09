package com.example.gymmie;

import java.util.Date;

public class ExerciseDetails extends Exercise {
    private int weight;
    private int reps;
    private int sets;
    private Date date;

    public ExerciseDetails(String exerciseName, String instruction) {
        super(exerciseName, instruction);
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
