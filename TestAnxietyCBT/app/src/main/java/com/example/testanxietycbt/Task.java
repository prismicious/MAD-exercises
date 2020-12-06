package com.example.testanxietycbt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Task implements Serializable {
    public String TaskName;
    public String TimeTaskCompleted;
    public int maxid;

    public Task(String taskName, String timeTaskCompleted) {
        TaskName = taskName;
        TimeTaskCompleted = timeTaskCompleted;
    }
}


