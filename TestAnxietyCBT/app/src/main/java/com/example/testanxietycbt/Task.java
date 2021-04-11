package com.example.testanxietycbt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Task implements Serializable {
    public String TaskName;
    public String TimeTaskCompleted;
    public String input1, input2, input3, input4, rateBefore, rateAfter;

    public Task(String taskName, String timeTaskCompleted, String input1, String input2, String input3, String input4, String rateBefore, String rateAfter) {
        TaskName = taskName;
        TimeTaskCompleted = timeTaskCompleted;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.rateBefore = rateBefore;
        this.rateAfter = rateAfter;
    }

    public Task() {
    }
}


