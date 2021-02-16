package com.example.testanxietycbt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.prefs.Preferences;

public class loginActivityFinal extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rememberMe = findViewById(R.id.rememberme);

        signIn = (Button) findViewById(R.id.Loginbutton);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences userLoginPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            rememberMe.setChecked(true);
            {
                String email = userLoginPreferences.getString("email", "");
                String password = userLoginPreferences.getString("password", "");
                editTextEmail.setText(email);
                editTextPassword.setText(password);
                userLogin();

            }
        } else if (checkbox.equals("false")){
            Toast.makeText(this, "Please Sign In.", Toast.LENGTH_SHORT).show();
        }

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(loginActivityFinal.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(loginActivityFinal.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.Loginbutton:
                userLogin();
                break;

        }
    }

    private void userLogin()
    {
        rememberMe = findViewById(R.id.rememberme);
        SharedPreferences preferences = getSharedPreferences("UserLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (rememberMe.isChecked()){

            editor.putString("email", editTextEmail.getText().toString().trim());
            editor.putString("password", editTextPassword.getText().toString().trim());
            editor.apply();

        } else {
            editor.putString("email", "");
            editor.putString("password", "");
            editor.apply();
        }

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextPassword.setError("Email is required!");
            editTextPassword.requestFocus();
            return;
        }


        if (password.isEmpty()){
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Password needs to be atleast 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        
        progressBar.setVisibility(View.VISIBLE);
        
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(loginActivityFinal.this, "Login successful!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(loginActivityFinal.this, homeActivity.class));
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(loginActivityFinal.this, "Failed to login! Please check credentials.", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


}
