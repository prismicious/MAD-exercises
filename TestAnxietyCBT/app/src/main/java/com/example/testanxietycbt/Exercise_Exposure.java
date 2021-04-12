package com.example.testanxietycbt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testanxietycbt.ui.exercise1.Exercise1_SectionsPagerAdapter;
import com.example.testanxietycbt.ui.exercise3.Exercise3_SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static bolts.Task.delay;

public class Exercise_Exposure extends AppCompatActivity {
Button btn;
String choice;
Button writtenbtn;
Button verbalbtn;
Button complete;
TextView text;
TextView textTitle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref2 = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    private int id;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        writtenbtn = findViewById(R.id.writtenbutton);
        verbalbtn = findViewById(R.id.verbalbutton);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise3_exposure);
        Exercise3_SectionsPagerAdapter sectionsPagerAdapter = new Exercise3_SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
       // TabLayout tabs = findViewById(R.id.tabs);
       // tabs.setupWithViewPager(viewPager);
        TabLayout dots = (TabLayout)findViewById(R.id.tablayout);
        dots.setupWithViewPager(viewPager, true); // <-- magic here!



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick2(View v){
        choice = "written";
        Log.i("Choice => ", choice);
        textTitle = findViewById(R.id.tipTitle7);
        textTitle.setText("Written");
        complete = findViewById(R.id.completebutton);
        complete.setVisibility(View.VISIBLE);
        text = findViewById(R.id.tipTitle6);
        text.setText("\nSteps:\n1. Allocate time for test exam\n2. Do a practice test on the subject. \n3. Treat it as if it was the real exam.");




    }

    public void onClick3(View v){
        choice = "verbal";
        Log.i("Choice => ", choice);
        textTitle = findViewById(R.id.tipTitle7);
        textTitle.setText("Verbal");
        complete = findViewById(R.id.completebutton);
        complete.setVisibility(View.VISIBLE);
        text = findViewById(R.id.tipTitle6);
        text.setText("\nSteps:\n1. Practice the exam by presenting for others.\n2. Choose one person, thing or pet to present the subject matter of the exam to.\n 3. Treat it as if it was the real exam");

    }

    public void onClick(View v){
        SharedPreferences get = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = get.edit();
        id = get.getInt("id", 0);
        editor.putInt("id",id+1);
        id++;
        while (!editor.commit()) {
            delay(100);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        Task task = new Task("Simulation task", currentDateAndTime, "", "", "", "", "", "");
        ref2.child(String.valueOf(id)).setValue(task);
        DatabaseReference activitiesCompletedRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activities Done");
        ActivitiesDone ad = new ActivitiesDone(1);
        activitiesCompletedRef.setValue(ad);
        startActivity(new Intent(Exercise_Exposure.this, Dashboard.class));

    }
}