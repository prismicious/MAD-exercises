package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static bolts.Task.delay;


public class MainActivity extends AppCompatActivity {
    int numberC = 0;
    int id = 0;
    public String inputText1;
    public String inputText2;
    public String inputText3;
    public String inputText4;
    public Button btn;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref2 = database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Activity");
    DatabaseReference ref3 = database.getReference().child("Test");
    DatabaseReference ref = database.getReference();
    int activitycount = 0;
    String sActivitycount = String.valueOf(activitycount);
    public String currentDateandTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_add:
                            selectedFragment = new DecatastrophizingFragment();

                            break;
                        case R.id.nav_test:
                            selectedFragment = new AssignFragment();
                            break;
                        case R.id.nav_test2:
                            selectedFragment = new HomeFragment();
                            break;
                    }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, selectedFragment.toString()).commit();

                    return true;
                }
            };

    public void onClick_decata1(View v)
    {
        TextInputLayout input = findViewById(R.id.textField_decata1);
        TextInputLayout input3 = findViewById(R.id.textField_decata3);
        inputText1 = input.getEditText().getText().toString();
        Log.i("Input 1 => ", inputText1);
        Fragment fragment = new DecatastrophizingFragment2();
        GoToNextFragment(fragment);
        editor = prefs.edit();
        editor.putString("Answer1", inputText1);
        editor.apply();

    }

    public void onClick_decata2(View v)
    {
        TextInputLayout input = findViewById(R.id.textField_decata2);
        inputText2 = input.getEditText().getText().toString();
        Log.i("Input 2 => ", inputText2);
        Fragment fragment = new DecatastrophizingFragment3();
        GoToNextFragment(fragment);
        editor = prefs.edit();
        editor.putString("Answer2", inputText2);
        editor.apply();
    }

    public void onClick_decata3(View v)
    {
        TextInputLayout input = findViewById(R.id.textField_decata3);
        inputText3 = input.getEditText().getText().toString();
        Log.i("Input 3 => ", inputText3);
        Fragment fragment = new DecatastrophizingFragment4();
        GoToNextFragment(fragment);
        editor = prefs.edit();
        editor.putString("Answer3", inputText3);
        editor.apply();
    }
    public void onClick_decata4(View v)
    {
        SharedPreferences get = getSharedPreferences("prefs", MODE_PRIVATE);
        TextInputLayout input = findViewById(R.id.textField_decata4);
        inputText4 = input.getEditText().getText().toString();
        Log.i("Input 4 => ", inputText4);
        //Fragment fragment = new DecatastrophizingFragment4();
       // GoToNextFragment(fragment);
        Log.i("here?", "yes");
        editor = prefs.edit();
        editor.putString("Answer4", inputText4);
        editor.apply();
        Intent i = new Intent(MainActivity.this, DecataOverviewFragment.class);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Task task = new Task("Decatastrophizing Task", currentDateandTime, inputText1, inputText2, inputText3, inputText4);
      //  ref3.setValue(task);
        id = get.getInt("id", 0);
        editor.putInt("id",id+1);
        id++;
        while (!editor.commit()) {
           delay(100);
        }
        Log.i("Increment", String.valueOf(get.getInt("id", 0)));
        ref2.child(String.valueOf(id)).setValue(task);
        startActivity(i);

    }

    private void checkData() {


    }


    /* public void onClick_test(View v){
        Intent i = new Intent(MainActivity.this, DecataOverviewFragment.class);
        startActivity(i);
    } */

    public void GoToNextFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // fragmentTransaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right, R.animator.enter_from_right, R.animator.exit_to_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
