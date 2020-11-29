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
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    int numberC = 0;
    String inputText1, inputText2, inputText3, inputText4;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

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
        startActivity(i);
    }

    /* public void onClick_test(View v){
        Intent i = new Intent(MainActivity.this, DecataOverviewFragment.class);
        startActivity(i);
    } */

    public void GoToNextFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right, R.animator.enter_from_right, R.animator.exit_to_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
