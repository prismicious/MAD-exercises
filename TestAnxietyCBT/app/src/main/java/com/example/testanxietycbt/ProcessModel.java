package com.example.testanxietycbt;

import java.io.Serializable;

public class ProcessModel implements Serializable {
    public String name, description, date, time;

    public ProcessModel(String name, String description, String date, String time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }
}
