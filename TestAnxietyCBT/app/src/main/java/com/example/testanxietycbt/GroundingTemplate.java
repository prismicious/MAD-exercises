package com.example.testanxietycbt;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class GroundingTemplate extends AppCompatActivity {

    TextView exerciseTextView;
    TextView exerciseSubtitle;
    Random rand;
    Button exerciseButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grounding_template);

        progressBar = findViewById(R.id.exerciseProgressBar);
        exerciseTextView = findViewById(R.id.exercise);
        exerciseSubtitle = findViewById(R.id.exerciseSubtitle);
        exerciseButton = findViewById(R.id.exerciseButton);
        rand = new Random();
        int upper_bound = 1;
        final int exerciseNumber = rand.nextInt(upper_bound);
        exerciseButton.setEnabled(false);
        exerciseButton.setTextColor(Color.BLACK);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                exerciseButton.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                Context contextInstance = getApplicationContext();
                exerciseButton.setTextSize(12);
                exerciseButton.setText("Continue");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    exerciseButton.setBackgroundTintList(contextInstance.getResources().getColorStateList(R.color.colorPrimary));
                }
                exerciseButton.setEnabled(true);
            }
        }.start();


        Log.i("Random","Randomlygenerated number = " + exerciseNumber);
        switch (exerciseNumber){
            case 0:
                exerciseTextView.setText("Take ten slow breaths. \n\n Focus your attention fully on each breath, on the way in and on the way out. \n\n Audible count the number of the breath as you exhale.");
                exerciseSubtitle.setText("The ten quick");
                break;
            case 1:
                exerciseTextView.setText("example text2");
                exerciseSubtitle.setText("Example subtitle2");
                break;
            case 3:
                exerciseTextView.setText("example text3");
                exerciseSubtitle.setText("Example subtitle3");
                break;
            case 4:
                exerciseTextView.setText("example text4");
                exerciseSubtitle.setText("Example subtitle4");
                break;

        }

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroundingTemplate.this, MainActivity.class));
            }
        });
    }


}
