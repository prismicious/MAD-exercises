package com.example.testanxietycbt;

import java.io.Serializable;

public class ActivitiesDone implements Serializable {
    public int amount;

    public ActivitiesDone() {
    }

    public ActivitiesDone(int activitiesCompleted) {
        this.amount = activitiesCompleted;
    }
}


