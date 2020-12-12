package com.example.firebaseauthproject;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText SignInMail, SignInPass;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // set the view now
        setContentView(R.layout.activity_signin);
        SignInMail = findViewById(R.id.SignInMail);
        SignInPass = findViewById(R.id.SignInPass);
        Button signInButton = findViewById(R.id.SignInButton);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(v -> {
            String email = SignInMail.getText().toString();
            final String password = SignInPass.getText().toString();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                return;
            }
            //authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignInActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 8) {
                                Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        });
    }

    public void NavigateSignUp(View v) {
        Intent inent = new Intent(this, SignUpActivity.class);
        startActivity(inent);
    }
    public void NavigateForgetMyPassword(View v) {
        Intent inent = new Intent(this, ResetPasswordActivity.class);
        startActivity(inent);
    }
}
