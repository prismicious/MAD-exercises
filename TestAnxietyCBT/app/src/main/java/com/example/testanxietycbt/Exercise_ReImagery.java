package com.example.testanxietycbt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testanxietycbt.ui.exercise2.Exercise2_SectionsPagerAdapter;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static bolts.Task.delay;

public class Exercise_ReImagery extends AppCompatActivity {
Button btn;
String choice;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref2 = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    private int id;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2_reimagery);
        Exercise2_SectionsPagerAdapter sectionsPagerAdapter = new Exercise2_SectionsPagerAdapter(this, getSupportFragmentManager());
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
        TextInputLayout Text1 = findViewById(R.id.textField_reimagery1);
        String input1 = Text1.getEditText().getText().toString();
        Log.i("input", "Input1 => " + input1);
        TextInputLayout Text2 = findViewById(R.id.textField_reimagery2);
        String input2 = Text2.getEditText().getText().toString();

        TextInputLayout Text3 = findViewById(R.id.textField_reimagery3);
        String input3 = Text3.getEditText().getText().toString();

        TextInputLayout Text4 = findViewById(R.id.textField_reimagery4);
        String input4 = Text4.getEditText().getText().toString();

        Slider distressRating = findViewById(R.id.slider1);
        String sliderRating = String.valueOf(distressRating.getValue());
        Log.i("sliderRating", "sliderRating => " + sliderRating);
        String sliderRatingPercent = sliderRating + "%";

        Slider distressRatingAfter = findViewById(R.id.slider_rerate);
        String sliderRatingAfter = String.valueOf(distressRatingAfter.getValue());
        String sliderRatingPercentAfter = sliderRatingAfter + "%";
        Task task = new Task("Reimagery task", currentDateAndTime, input1, input2, input3, input4, sliderRatingPercent, sliderRatingPercentAfter);
        ref2.child(String.valueOf(id)).setValue(task);
        DatabaseReference activitiesCompletedRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activities Done");
        ActivitiesDone ad = new ActivitiesDone(3);
        activitiesCompletedRef.setValue(ad);
        startActivity(new Intent(Exercise_ReImagery.this, Dashboard.class));

    }
}