package com.example.gymmie;

public class Exercise {
    private String exerciseName;
    private String instruction;

    public Exercise(String exerciseName, String instruction) {
        this.exerciseName = exerciseName;
        this.instruction = instruction;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
