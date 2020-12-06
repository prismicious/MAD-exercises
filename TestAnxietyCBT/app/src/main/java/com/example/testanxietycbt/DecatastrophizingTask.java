package com.example.testanxietycbt;

import java.io.Serializable;
import java.util.Date;

public class DecatastrophizingTask extends Task implements Serializable {
    public String input1;
    public String input2;
    public String input3;
    public String input4;

    public DecatastrophizingTask(String taskName, String timeTaskCompleted, String input1, String input2, String input3, String input4) {
        super(taskName, timeTaskCompleted);
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
    }
}
