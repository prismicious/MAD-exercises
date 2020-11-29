package com.example.testanxietycbt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Timeline {
    List<Task> ListOfTasks = new ArrayList<Task>();
    Date TimeLineStartTime = Calendar.getInstance().getTime();

    public Timeline(List<Task> listOfTasks, Date timeLineStartTime)
    {
        ListOfTasks = listOfTasks;
        TimeLineStartTime = timeLineStartTime;
    }
}
