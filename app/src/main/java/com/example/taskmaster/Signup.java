package com.example.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

public class Signup extends AppCompatActivity {

    private static final String TAG = "Signup" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        configureAmplify();

        EditText username = findViewById(R.id.username_input);
        EditText email = findViewById(R.id.email_input);
        EditText password = findViewById(R.id.password_input);




        TextView goToSignin = findViewById(R.id.go_to_signin);
        goToSignin.setOnClickListener(view -> {
            Intent toSignin = new Intent(Signup.this, Signin.class);
            startActivity(toSignin);

        });


        Button signupBtn = findViewById(R.id.signup_Btn);
        signupBtn.setOnClickListener(view -> {
            String userNameData = username.getText().toString();
            String emailData = email.getText().toString();
            String passwordData = password.getText().toString();

            registerUser(userNameData,emailData, passwordData );
        });
    }


  public void registerUser(String username, String email, String password){
    AuthSignUpOptions options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build();
    Amplify.Auth.signUp(email, password, options,
            success -> {
        Log.i(TAG, "Successfully Signed up " + success.toString());
        Intent goToVerification = new Intent(Signup.this,VerificationActivity.class);
        goToVerification.putExtra("UserEmail",email);
        goToVerification.putExtra("password",password);
        goToVerification.putExtra("UserName",username);
        startActivity(goToVerification);
            },
            error -> Log.i(TAG, "Sign up failed", error)
    );
}

    private void configureAmplify() {

        try {
            System.out.println("**********TRY METHOD************");
            Amplify.addPlugin(new AWSDataStorePlugin());
            // stores records locally
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }

    }
}