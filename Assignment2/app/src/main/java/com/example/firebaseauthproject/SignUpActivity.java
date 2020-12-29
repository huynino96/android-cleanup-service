package com.example.firebaseauthproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText SignUpMail,SignUpPass;
    Button SignUpButton;
    private FirebaseAuth auth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUpMail = findViewById(R.id.SignUpMail);
        SignUpPass = findViewById(R.id.SignUpPass);
        auth=FirebaseAuth.getInstance();
        SignUpButton = findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(v -> {
            String email = SignUpMail.getText().toString();
            String pass = SignUpPass.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
            }
            if (pass.length() == 0) {
                Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
            }
            if (pass.length() < 6) {
                Toast.makeText(getApplicationContext(),"Password must be more than 6 digit",Toast.LENGTH_LONG).show();
            }
            else {
                auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(SignUpActivity.this, task -> {

                            if (!task.isSuccessful()) {
                                String message = task.getException().getMessage();
                                Toast.makeText(SignUpActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                            }
                            else {
                                startActivity(new Intent(SignUpActivity.this, EditProfileActivity.class));
                                finish();
                            }
                        });}
        });
    }

    public void navigate_sign_in(View v){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}