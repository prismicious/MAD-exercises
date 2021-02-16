package com.example.testanxietycbt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static bolts.Task.delay;

public class PredictionActivity extends AppCompatActivity {

    String selectedChip = "good";
    TextInputLayout input;
    int Year, Month, Day, id;
    Chip goodChip, okChip, poorChip;
    ChipGroup chipGroup;
    Button nextButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public String currentDateandTime;
    String inputText;
    CheckBox reminderCheckbox;
    String checkBoxChecked = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        input = findViewById(R.id.textField_prediction1);
        setContentView(R.layout.activity_prediction);
        Calendar calendar ;
        reminderCheckbox = findViewById(R.id.reminderCheckbox);
        reminderCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    checkBoxChecked = "Y";
                }
                else {
                    checkBoxChecked = "N";
                }
            }
        });
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

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        final MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select date");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        final Button dateButton = findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.i("DatePicker", materialDatePicker.getHeaderText());
                Button dateButton = (Button)findViewById(R.id.dateButton);
                dateButton.setText(materialDatePicker.getHeaderText());
                dateButton.setHint(materialDatePicker.getHeaderText());
            }
        });

        nextButton = findViewById(R.id.nextButton1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = findViewById(R.id.textField_prediction1);
                inputText = input.getEditText().getText().toString();
                if (inputText.isEmpty()){
                    input.setError("Please enter something!");
                    input.requestFocus();
                    return;
                }
                    SharedPreferences get = getSharedPreferences("prefs", MODE_PRIVATE);
                    editor = prefs.edit();
                    id = get.getInt("id", 0);
                    editor = prefs.edit();
                    editor.putInt("id",id+1);
                    id++;
                    while (!editor.commit()) {
                        delay(100);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault());
                    currentDateandTime = sdf.format(new Date());
                    inputText = input.getEditText().getText().toString();
                    Task task = new Task("Prediction Activity", currentDateandTime, inputText, selectedChip, dateButton.getText().toString(), checkBoxChecked);
                    ref.child(String.valueOf(id)).setValue(task);
                    startActivity(new Intent(PredictionActivity.this, Dashboard.class));

            }
        });
    }

}
