package com.example.testanxietycbt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testanxietycbt.ui.exercise1.Exercise1_SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static bolts.Task.delay;

public class Exercise_StudyTips extends AppCompatActivity {
Button btn;
String choice;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref2 = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    private int id;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1_studytips);
        Exercise1_SectionsPagerAdapter sectionsPagerAdapter = new Exercise1_SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
       // TabLayout tabs = findViewById(R.id.tabs);
       // tabs.setupWithViewPager(viewPager);
        TabLayout dots = (TabLayout)findViewById(R.id.tablayout);
        dots.setupWithViewPager(viewPager, true); // <-- magic here!

        btn = findViewById(R.id.scheduleButton);
        RadioGroup radioG = findViewById(R.id.radiogroup);
        if (radioG != null) {
            if (radioG.getCheckedRadioButtonId() == R.id.radio1){
                choice = "yes";
                Log.i("Choice", "Choice => " + choice);
            }
        }

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
        Task task = new Task("Study tips task", currentDateAndTime, "", "", "", "");
        ref2.child(String.valueOf(id)).setValue(task);
        startActivity(new Intent(Exercise_StudyTips.this, Dashboard.class));

    }
}