package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homeActivity extends AppCompatActivity {

    User user;
    ProcessModel process;
    int seconds = 600;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int countdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("countdown", 5);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Process")) {
                    Log.i("testxx", "Process found");
                }
                else {
                    Log.i("testxx", "Process not found");
                    startActivity(new Intent(homeActivity.this, Process.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DatabaseError", error.toString());
            }
        });



        DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        DatabaseReference processRef = ref.child("Process");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.i("User", user.fullName);
                TextView title = findViewById(R.id.welcome);

                if (user != null) {
                    title.setText("Welcome " + user.fullName);
                }
                else {
                    title.setText("Welcome ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        processRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                process = dataSnapshot.getValue(ProcessModel.class);
                //Log.i("User", user.fullName);
                TextView process_name = findViewById(R.id.process_name);
                TextView process_date = findViewById(R.id.process_datetest);
                TextView process_time = findViewById(R.id.process_timetest);
                TextView process_expectation = findViewById(R.id.process_expectation_input);
                TextView process_countdown = findViewById(R.id.countdown);
                long countdownInSecs = NotificationService.timeleft;

                if (process != null) {
                    process_name.setText(process.name);
                    process_date.setText(process.time);
                    process_time.setText(process.date);
                    process_expectation.setText(process.prediction);
                    process_countdown.setText(String.valueOf(countdownInSecs) + " seconds");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new DecatastrophizingFragment();

                    switch (item.getItemId()){
                        //  case R.id.nav_add:
                        //     selectedFragment = new DecatastrophizingFragment();

                        //    break;
                        case R.id.nav_test:
                            startActivity(new Intent(homeActivity.this, Dashboard.class));
                            //  selectedFragment = new AssignFragment();
                            break;
                        case R.id.nav_test2:
                            startActivity(new Intent(homeActivity.this, homeActivity.class));
                            break;

                        default:
                            selectedFragment = new DecatastrophizingFragment();
                    }


                    return true;
                }
            };

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Hello", "Starting service");


            countdown = prefs.getInt("countdown", 2);

            if (countdown == 0) {
                Log.i("zxcv","Starting new service!");
                startService(new Intent(this, NotificationService.class));
            }
            else {
                Log.i("zxcv","Service already started!");
            }
    }
}
