package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    int currentCalc = 0;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        TextView counterText = findViewById(R.id.counter);
        i++;
        counterText.setText(String.valueOf(i));
    }

    public void onClickMinus(View v){
        TextView counterText = findViewById(R.id.counter);
        i--;
        if (i < 0)
        {
            i = 0;
            counterText.setText(String.valueOf(i));
        }
        else {
            counterText.setText(String.valueOf(i));
        }
    }

    public void onClickCalc(View v){
        TextView calcText = findViewById(R.id.result);
        currentCalc = i*3;
        if (currentCalc != 0){
            calcText.setBackgroundColor(Color.BLACK);
            calcText.setTextColor(Color.WHITE);
        }
        calcText.setText(String.valueOf(currentCalc));
    }

    public void onClickClear(View v){
        i = 0;
        currentCalc = 0;
        TextView counterText = findViewById(R.id.counter);
        TextView calcText = findViewById(R.id.result);
        counterText.setText("0");
        calcText.setText("0");
        calcText.setBackgroundColor(Color.WHITE);
        calcText.setTextColor(Color.BLACK);
    }


}
