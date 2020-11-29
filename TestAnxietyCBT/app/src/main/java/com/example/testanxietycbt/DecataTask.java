package com.example.testanxietycbt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DecataTask extends AppCompatActivity {

    String Answer1, Answer2, Answer3, Answer4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.i("Test ", "Test!?");
        GetData();
    }

    public void GetData(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_WORLD_READABLE);
        Answer1 = prefs.getString("Answer1", "");
        Answer2 = prefs.getString("Answer2", "");
        Answer3 = prefs.getString("Answer3", "");
        Answer4 = prefs.getString("Answer4", "");

        Log.i("Answer1 =>", Answer1 + "Answer2 => " + Answer2 + "Answer3 => " + Answer3 + "Answer4 => " + Answer4);
    }
}
