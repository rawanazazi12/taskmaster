package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG ="VerificationActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        EditText verificationCode = findViewById(R.id.verification_code_input);
        Button verificationBtn = findViewById(R.id.verification_Btn);

        Intent intent = getIntent();
        String username = intent.getStringExtra("UserEmail");
        String loggedUserName = intent.getStringExtra("UserName");

        verificationBtn.setOnClickListener(view -> {
            String code = verificationCode.getText().toString();
            verifyUser(username,code,loggedUserName);


        });

    }



    public void verifyUser(String username, String code, String UserName){
        Amplify.Auth.confirmSignUp(
                username,
                code,
                success ->{
                    Log.i(TAG, success.isSignUpComplete()
                            ? "Confirm signUp succeeded"
                            : "Confirm sign up not complete");
                    Intent goToSignIn = new Intent(VerificationActivity.this, Signin.class);
                    goToSignIn.putExtra("verifiedUserName", username);
                    goToSignIn.putExtra("verifiedUser",UserName);
                    startActivity(goToSignIn);


                },
                error -> Log.i(TAG, error.toString())
        );
    }
}