package com.example.testanxietycbt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DecataOverviewFragment extends AppCompatActivity {


    String Answer1, Answer2, Answer3, Answer4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_decata_overview);
        Log.i("Test ", "Test!?");
        GetDataAndAssignText();
    }

    public void GetDataAndAssignText(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_WORLD_READABLE);
        Answer1 = prefs.getString("Answer1", "");
        Answer2 = prefs.getString("Answer2", "");
        Answer3 = prefs.getString("Answer3", "");
        Answer4 = prefs.getString("Answer4", "");
        TextView input1 = findViewById(R.id.input1);
        TextView input2 = findViewById(R.id.input2);
        TextView input3 = findViewById(R.id.input3);
        TextView input4 = findViewById(R.id.input4);
        input1.setText(Answer1);
        input2.setText(Answer2);
        input3.setText(Answer3);
        input4.setText(Answer4);

        Log.i("Answer1 =>", Answer1 + "Answer2 => " + Answer2 + "Answer3 => " + Answer3 + "Answer4 => " + Answer4);
    }
}
