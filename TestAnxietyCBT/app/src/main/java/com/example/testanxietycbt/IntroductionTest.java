package com.example.testanxietycbt;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.testanxietycbt.ui.main.SectionsPagerAdapter;

public class IntroductionTest extends AppCompatActivity {
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
        startActivity(new Intent(IntroductionTest.this, Process.class));
    }
}