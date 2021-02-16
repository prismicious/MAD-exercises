package com.example.testanxietycbt;

import java.io.Serializable;

public class ProcessModel implements Serializable {
    public String name, description, date, time, prediction;

    public ProcessModel() {
    }

    public ProcessModel(String name, String description, String date, String time, String prediction) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.prediction = prediction;
    }
}


