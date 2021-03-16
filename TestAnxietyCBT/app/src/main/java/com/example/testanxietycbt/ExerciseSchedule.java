package com.example.testanxietycbt;

public class ExerciseSchedule {
    public String Date;
    public String Time;
    public long SecondsTillExercise;



    public ExerciseSchedule(){}

    public ExerciseSchedule(String date, String time, long secondsTillExercise) {
        Date = date;
        Time = time;
        SecondsTillExercise = secondsTillExercise;
    }
}
