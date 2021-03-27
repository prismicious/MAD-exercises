package com.example.testanxietycbt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.testanxietycbt.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class Exercise_StudyTips extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_test);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
       // TabLayout tabs = findViewById(R.id.tabs);
       // tabs.setupWithViewPager(viewPager);
        TabLayout dots = (TabLayout)findViewById(R.id.tablayout);
        dots.setupWithViewPager(viewPager, true); // <-- magic here!

        btn = findViewById(R.id.scheduleButton);

    }

    public void onClick(View v){
        startActivity(new Intent(Exercise_StudyTips.this, Process.class));
    }
}