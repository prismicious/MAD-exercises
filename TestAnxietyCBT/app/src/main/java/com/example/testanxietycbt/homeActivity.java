package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class homeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    User user;
    ProcessModel process;
    int seconds = 600;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int countdown;
    int activityScheduled;
    FirebaseDatabase database;
    DatabaseReference rootRef;
    Button scheduleButton;
    private ExerciseSchedule exerciseSchedule;
    private String dateString;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int delay = 1000;
    String accentColor = "#AF4448";
    public long differenceInSeconds;
    TextView userID;
    private boolean datePicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("countdown", 5);
        activityScheduled = prefs.getInt("activityScheduled", 1);
        Log.i("ActivityScheduled", "Activityscheduled " + activityScheduled);

        TextView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(homeActivity.this, loginActivityFinal.class));
                SharedPreferences rememberMe = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor rememberEditor = rememberMe.edit();
                rememberEditor.putString("remember", "false");
                rememberEditor.apply();
            }
        });


        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {



                User user = snapshot.getValue(User.class);
                userID = findViewById(R.id.userID);
                if (user != null) {
                    userID.setText(user.fullName);
                }

                if (snapshot.hasChild("Process")) {
                    Log.i("testxx", "Process found");
                    ActivitiesDone ad = snapshot.getValue(ActivitiesDone.class);
                    int activitiesCompleted = ad.amount;
                    Log.i("Completed", String.valueOf(activitiesCompleted));
                    startActivity(new Intent(homeActivity.this, Exercise_Exposure.class));
                }
                else {
                    Log.i("testxx", "Process not found");
                    startActivity(new Intent(homeActivity.this, IntroductionTest.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("DatabaseError", error.toString());
            }
        });



        DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        DatabaseReference processRef = ref.child("Process");

        ref.child("NextActivity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 exerciseSchedule = snapshot.getValue(ExerciseSchedule.class);
                TextView date = findViewById(R.id.countdown);
                TextView time = findViewById(R.id.countdownTime);

                if (exerciseSchedule.Time.equals("")){
                    Log.i("TestActivity", "Activity Not scheduled!");

                    TextView scheduleExercise = findViewById(R.id.reschedulebutton);
                    if (datePicked == false) {

                        Toast.makeText(homeActivity.this, "No Exercise scheduled. Please schedule exercise!", Toast.LENGTH_LONG).show();
                        ObjectAnimator animY = ObjectAnimator.ofFloat(scheduleExercise, "translationY", -5f, 5f);
                        animY.setDuration(1000);//1sec
                        animY.setInterpolator(new BounceInterpolator());
                        animY.setRepeatCount(10);
                        animY.start();

                        /* Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(1000); //You can manage the blinking time with this parameter
                        anim.setStartOffset(20);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        scheduleExercise.startAnimation(anim); */
                        datePicked = true;
                    }
                    else {
                        scheduleExercise.clearAnimation();
                    }
                }

                if (exerciseSchedule != null) {
                    dateString = exerciseSchedule.Date;
                    date.setText(exerciseSchedule.Date);
                }

                if (exerciseSchedule != null) {
                    time.setText(exerciseSchedule.Time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

                if (process != null) {
                    process_name.setText(process.name);
                    process_date.setText(process.time);
                    process_time.setText(process.date);
                    process_expectation.setText(process.prediction);

                    Calendar now = Calendar.getInstance();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        final MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        final Button dialog_bt_time = findViewById(R.id.dateButton);

        dialog_bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();

            }
        });

       Button exerciseButton = findViewById(R.id.startExercise);
       exerciseButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               activityScheduled = 0;
               editor.putInt("activityScheduled", 0);
               editor.apply();
               TextView scheduleTitle = findViewById(R.id.activity_text);
               TextView countdownDate = findViewById(R.id.countdown);
               TextView countdownTime = findViewById(R.id.countdownTime);

               scheduleTitle.setText("");
               countdownDate.setText("");
               countdownTime.setText("");
                ExerciseSchedule empty = new ExerciseSchedule("","", 0);
               rootRef.child("NextActivity").setValue(empty);


               startActivity(new Intent(homeActivity.this, GroundingTemplate.class));
           }
       });

       final TextView reschedulebutton = findViewById(R.id.reschedulebutton);
       reschedulebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                datePicker();
           }
       });

       TextView rescheduleProcessButton = findViewById(R.id.rescheduleprocess);
       rescheduleProcessButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(homeActivity.this, Process.class));
           }
       });


    }

    public void datePicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                homeActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );


        Calendar max = Calendar.getInstance();
        max.get(Calendar.YEAR); // Initial year selection
        max.get(Calendar.MONTH); // Initial month selection
        max.add(Calendar.DAY_OF_MONTH, 3);
        dpd.setAccentColor(accentColor);
        dpd.setMinDate(now);
        dpd.setMaxDate(max);
        // If you're calling this from a support Fragment
        dpd.show(getFragmentManager(), "Datepickerdialog");
        // materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
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
    protected void onStart() {
        Log.i("ActivityScheduled", "is this it? " + activityScheduled);
        super.onStart();
    }

    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                Log.i("DateTest", "Testing delay");
                TextView dateT = findViewById(R.id.countdown);
                TextView timeT = findViewById(R.id.countdownTime);
                Log.i("DateTest", "TimeTest" +(String) timeT.getText());
                String date = String.valueOf(dateT.getText());
                String time = String.valueOf(timeT.getText());
                String dateAndTime = "";
                if (date != "" && time != "") {
                    dateAndTime = date + " " + time;
                }
                Log.i("DateTest", "DateAndTime " + dateAndTime);
                Log.i("DateTest","Testdate + " + dateString);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
                Calendar c = Calendar.getInstance();
                Date today = null;
                Date stringDate = null;
                String getCurrentDateTime = sdf.format(c.getTime());
                try {
                    today = sdf.parse(getCurrentDateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("DateTest","CurrentTime " + getCurrentDateTime);
                try {
                    stringDate = sdf.parse(dateAndTime);
                    Log.i("DateTest", "Stringdate " + stringDate);
                    Button exerciseButton = findViewById(R.id.startExercise);
                    if (date.isEmpty() && time.isEmpty()){
                        exerciseButton.setText("PLEASE SCHEDULE AN EXERCISE!");
                    }

                    if (getCurrentDateTime.compareTo(dateAndTime) >= 0 ){
                        Log.i("DateTest", "Time surpassed.");
                        exerciseButton.setEnabled(true);
                        exerciseButton.setText("EXERCISE AVAILABLE!");


                    }
                    else {
                        Log.i("DateTest", "Havent reached time yet.");
                        exerciseButton.setEnabled(false);
                        exerciseButton.setText("EXERCISE NOT AVAILABLE YET");
                    }
                } catch (ParseException e) {
                   Log.i("DateTest", "exception " + e);
                }

                if (today != null && stringDate != null)
                differenceInSeconds = Math.abs((today.getTime() - stringDate.getTime()) / 1000);
                Log.i("Difference", "Difference in seconds => " + differenceInSeconds);
                ExerciseSchedule scheduledExercise = new ExerciseSchedule(date, time, differenceInSeconds);
                rootRef.child("NextActivity").setValue(scheduledExercise);

                final Button dateButton = findViewById(R.id.dateButton);
                final TextView reschedulebutton = findViewById(R.id.reschedulebutton);

                rootRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                rootRef.child("NextActivity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ExerciseSchedule schedule = snapshot.getValue(ExerciseSchedule.class);
                        TextView activity_text = findViewById(R.id.activity_text);
                        if (snapshot.hasChild("NextActivity")) {
                            if (schedule.Date.isEmpty() && schedule.Time.isEmpty()) {
                                activity_text.setVisibility(View.INVISIBLE);
                                Log.i("ActivityTest", "Activity is Not scheduled setting Datebutton to VIS");
                                //dateButton.setEnabled(true);
                                //reschedulebutton.setEnabled(false);

                            } else {
                                activity_text.setVisibility(View.VISIBLE);
                                Log.i("ActivityTest", "Activity is Activity is Scheduled! at " + schedule.Date + " " + schedule.Time + "setting Datebutton to INVIS");
                                //dateButton.setEnabled(false);
                                //reschedulebutton.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int modifiedMonthInt = monthOfYear+1;
        String modifiedMonth = String.valueOf(monthOfYear+1);
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(homeActivity.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        timePickerDialog.setAccentColor(accentColor);
        timePickerDialog.show(getFragmentManager(),"");
        Calendar minTimeCalendar = Calendar.getInstance();
        minTimeCalendar.setTime(minTimeCalendar.getTime());
        int currentHour = now.get(Calendar.HOUR);
        int currentMin = now.get(Calendar.MINUTE);
        int currentSecond = now.get(Calendar.SECOND);
        timePickerDialog.setMinTime(new Timepoint(minTimeCalendar.get(Calendar.HOUR), 0, 0));
        TextView activity_countdown = findViewById(R.id.countdown);

        if (modifiedMonthInt < 10) {
            activity_countdown.setText(dayOfMonth + "/" + "0" + modifiedMonth);
        }
        else {
            activity_countdown.setText(dayOfMonth + "/" + modifiedMonth);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        TextView countdownTime = findViewById(R.id.countdownTime);
        String hourOfDayString = "";
        String minuteString = "";
        if (hourOfDay < 10) {
            hourOfDayString = "0" + hourOfDay;
        }
        else {
            hourOfDayString = String.valueOf(hourOfDay);
        }

        if (minute < 10) {
            minuteString = "0" + minute;
        }
        else {
            minuteString = String.valueOf(minute);
        }
        countdownTime.setText(hourOfDayString + ":" + minuteString);
        Log.i("activityScheduled", "Before assert =>" + activityScheduled);
        activityScheduled = 1;
        editor.putInt("activityScheduled", activityScheduled);
        editor.apply();
        Log.i("activityScheduled", "After assert =>" + activityScheduled);

        startService(new Intent(this, NotificationService.class));
       // startActivity(new Intent(homeActivity.this, homeActivity.class));
    }

}
