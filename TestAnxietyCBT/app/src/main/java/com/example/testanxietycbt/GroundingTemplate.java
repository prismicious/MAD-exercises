package com.example.testanxietycbt;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class GroundingTemplate extends AppCompatActivity {

    TextView exerciseTextView;
    TextView exerciseSubtitle;
    Random rand;
    Button exerciseButton;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public int activitiesDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grounding_template);

        progressBar = findViewById(R.id.exerciseProgressBar);
        exerciseTextView = findViewById(R.id.exercise);
        exerciseSubtitle = findViewById(R.id.exerciseSubtitle);
        exerciseButton = findViewById(R.id.exerciseButton);
        rand = new Random();
        int upper_bound = 5;
        final int exerciseNumber = rand.nextInt(upper_bound);
        exerciseButton.setEnabled(false);

        final DatabaseReference activitiesCompletedRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activities Done");

        activitiesCompletedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ActivitiesDone ad = snapshot.getValue(ActivitiesDone.class);
                activitiesDone = ad.amount;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                exerciseButton.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                exerciseButton.setTextSize(12);
                exerciseButton.setText("Continue");

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
                exerciseTextView.setText("Move your body");
                exerciseSubtitle.setTextSize(12);
                exerciseSubtitle.setText("Do a few exercises or stretches. You could try jumping jacks, jumping up and down, jumping rope, jogging in place, or stretching different muscle groups one by one.\n" +
                        "\n" +
                        "Pay attention to how your body feels with each movement and when your hands or feet touch the floor or move through the air. How does the floor feel against your feet and hands? If you jump rope, listen to the sound of the rope in the air and when it hits the ground.");
                break;
            case 3:
                exerciseTextView.setText("5 4 3 2 1 method");
                exerciseSubtitle.setTextSize(12);
                exerciseSubtitle.setText("Working backward from 5, use your senses to list things you notice around you. For example, you might start by listing five things you hear, then four things you see, then three things you can touch from where you’re sitting, two things you can smell, and one thing you can taste.\n" +
                        "\n" +
                        "Make an effort to notice the little things you might not always pay attention to, such as the color of the flecks in the carpet or the hum of your computer.");
                break;
            case 4:
                exerciseTextView.setText("Practice self-kindness");
                exerciseSubtitle.setTextSize(12);
                exerciseSubtitle.setText("Repeat kind, compassionate phrases to yourself:\n" +
                        "\n" +
                        "“You’re having a rough time, but you’ll make it through.”\n" +
                        "“You’re strong, and you can move through this pain.”\n" +
                        "“You’re trying hard, and you’re doing your best.”\n" +
                        "Say it, either aloud or in your head, as many times as you need.");
                break;

        }

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (activitiesDone){
                    case 0: startActivity(new Intent(GroundingTemplate.this, Exercise_StudyTips.class));
                    break;

                    case 1: startActivity(new Intent(GroundingTemplate.this, MainActivity.class));
                    break;

                    case 2: startActivity(new Intent(GroundingTemplate.this, Exercise_ReImagery.class));
                    break;

                    default:
                        Toast.makeText(getApplicationContext(), "No activties left! Thanks for using the app!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
