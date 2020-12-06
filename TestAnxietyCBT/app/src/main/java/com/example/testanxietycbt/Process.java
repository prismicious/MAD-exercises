package com.example.testanxietycbt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Log.i("Date", String.valueOf(Month));
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        final MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        final Button dialog_bt_time = findViewById(R.id.dialog_bt_time);

        dialog_bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(Process.this, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Set the time");
                timePickerDialog.show(getFragmentManager(),"");
            }
        });

        final Button dialog_bt_date = (Button)findViewById(R.id.dialog_bt_date);

        dialog_bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                /*datePickerDialog = DatePickerDialog.newInstance(Process.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#0072BA"));

                datePickerDialog.setTitle("Select Date ");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog"); */

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
                TextInputLayout desc = findViewById(R.id.desc);
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

                Toast.makeText(Process.this, "Process created succesfully!", Toast.LENGTH_SHORT).show();
                
                    nameString = name.getEditText().getText().toString();
                    descString = desc.getEditText().getText().toString();
                    timeString = dialog_bt_time.getText().toString();
                    dateString = dialog_bt_date.getText().toString();

                    ProcessModel thisProcess = new ProcessModel(nameString, descString, timeString, dateString);
                    DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Process");
                    ref.setValue(thisProcess);

                Log.i("Process", "Name: " + nameString + " desc: " + descString + " time: " +timeString + " date: " + dateString);

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
}
