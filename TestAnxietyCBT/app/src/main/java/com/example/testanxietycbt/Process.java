package com.example.testanxietycbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;
import java.util.Date;

import static bolts.Task.delay;

public class Process extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button button, button2;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    TimePickerDialog timePickerDialog;
    TextInputLayout name;
    TextInputLayout desc;
    int Year, Month, Day ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String descString, nameString, timeString, dateString;
    Chip goodChip, okChip, poorChip;
    ChipGroup chipGroup;
    String selectedChip = "good";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int countDownStarted = 0;
    public static boolean firstExercise = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        name = findViewById(R.id.name);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Log.i("Date", String.valueOf(Month));
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        goodChip = findViewById(R.id.chipGood);
        okChip = findViewById(R.id.chipOK);
        poorChip = findViewById(R.id.chipPoor);
        Log.i("TestChip", String.valueOf(goodChip.getId()));
        chipGroup = findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                if (checkedId == goodChip.getId()){
                    selectedChip = "good";
                }
                if (checkedId == okChip.getId()){
                    selectedChip = "ok";
                }

                if (checkedId == poorChip.getId()){
                    selectedChip = "poor";
                }
                Log.i("selectedChip", selectedChip);
            }
        });


        final MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        final Button dialog_bt_time = findViewById(R.id.dialog_bt_time);

        dialog_bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(Process.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                timePickerDialog.setAccentColor("#AF4448");
                int currentHour = now.get(Calendar.HOUR);
                int currentMin = now.get(Calendar.MINUTE);
                int currentSecond = now.get(Calendar.SECOND);
                timePickerDialog.setMinTime(new Timepoint(currentHour,currentMin, currentSecond));
                timePickerDialog.setTitle("Set the time");
                timePickerDialog.show(getFragmentManager(),"");
            }
        });

        final Button dialog_bt_date = (Button)findViewById(R.id.dialog_bt_date);

        dialog_bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //datePickerDialog.setMinDate(now);
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");


            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.i("DatePicker", materialDatePicker.getHeaderText());
                Button dateButton = (Button)findViewById(R.id.dialog_bt_date);
                dateButton.setText(materialDatePicker.getHeaderText());
                dateButton.setHint(materialDatePicker.getHeaderText());
            }
        });

        final Button submitButton = (Button)findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputLayout name = findViewById(R.id.name);
                String nameCheck = name.getEditText().getText().toString().trim();
                String descCheck = name.getEditText().getText().toString().trim();

                Log.i("Process", "test");
                Log.i("Process", "getTetx: " + dialog_bt_date.getText().toString());

                if (dialog_bt_date.getText().equals("Insert date")){
                    dialog_bt_date.setError("Please insert date");
                    return;
                }
                if (dialog_bt_time.getText().equals("Insert time")){
                    dialog_bt_time.setError("Please insert time");
                    return;
                }
                if (descCheck.isEmpty()){
                    desc.setError("Please add description!");
                    desc.requestFocus();
                    return;
                }
                if (nameCheck.isEmpty()){
                    name.setError("Please add name!");
                    name.requestFocus();
                    return;
                }

                Toast.makeText(Process.this, "Exam scheduled succesfully!", Toast.LENGTH_SHORT).show();
                    nameString = name.getEditText().getText().toString();
                    descString = "";
                    timeString = dialog_bt_time.getText().toString();
                    dateString = dialog_bt_date.getText().toString();

                    ProcessModel thisProcess = new ProcessModel(nameString, descString, timeString, dateString, selectedChip);
                    DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Process");
                    firstExercise = true;
                    ref.setValue(thisProcess);
                    countDownStarted = 1;

                Log.i("Process", "Name: " + nameString + " desc: " + descString + " time: " +timeString + " date: " + dateString);
                startActivity(new Intent(Process.this, homeActivity.class));
                SharedPreferences get = getSharedPreferences("prefs", MODE_PRIVATE);
                editor = prefs.edit();
                editor.putInt("countdown", countDownStarted);
            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String modifiedMonth = String.valueOf(Month+1);
        String date = Day + "/" + modifiedMonth + "/" + Year;
        Button dateButton = (Button)findViewById(R.id.dialog_bt_date);
        dateButton.setText(date);
        dateButton.setHint(date);
        Toast.makeText(Process.this, date, Toast.LENGTH_LONG).show();
        Log.i("Date", date);
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time;
        String formattedMin = "";
        String formattedHour = "";
        String min = "";
        String hour = "";
        hour = String.valueOf(hourOfDay);
        min = String.valueOf(minute);
        if (minute < 10) {
            formattedMin = "0" + min;
        }
        else {
            formattedMin = min;
        }

        if (hourOfDay < 10) {
            formattedHour = "0" + hour;
        }
        else {
            formattedHour = hour;
        }

        time = formattedHour + ":" + formattedMin;

        Button timeButton = (Button)findViewById(R.id.dialog_bt_time);
        timeButton.setText(time);
        timeButton.setHint(time);
        Toast.makeText(Process.this, time, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Hello", "Starting service");


            startService(new Intent(this, NotificationService.class));

    }
}
