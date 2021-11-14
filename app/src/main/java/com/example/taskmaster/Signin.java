package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class Signin extends AppCompatActivity {

    private static final String TAG = "Signin" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        Intent intent = getIntent();
        String USER = intent.getStringExtra("verifiedUser");
        EditText emailField = findViewById(R.id.email_signin_input);
        EditText passwordField = findViewById(R.id.password_signin_input);
        Button signInBtn = findViewById(R.id.signin_Btn);

        // if the user doesn't have an account go to signup
        TextView goToSignUp = findViewById(R.id.go_to_signup);
        goToSignUp.setOnClickListener(view -> {
            Intent goToSignup = new Intent(Signin.this, Signup.class);
            startActivity(goToSignup);
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        signInBtn.setOnClickListener(view -> {
            String emailData = emailField.getText().toString();
            String passwordData = passwordField.getText().toString();
            signInUser(emailData,passwordData);
            editor.putString("email",emailData);
            editor.putString("USER_NAME",USER);
            editor.apply();

        });


    }


    public void signInUser(String email, String password){
        Amplify.Auth.signIn(
                email,
                password,
                success -> {
                    Log.i(TAG, success.isSignInComplete()
                            ? "Successfully Signed In"
                            : "Sign in not complete");
                    Intent goToMainActivity = new Intent(Signin.this, MainActivity.class);
                    startActivity(goToMainActivity);
                },
                error -> Log.i(TAG, error.toString())
        );
    }
}