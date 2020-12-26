package com.example.firebaseauthproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_favorites:
                            Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.action_home:
//                            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                            Intent intentMap = new Intent(HomeActivity.this, MapsActivity.class);
                            startActivity(intentMap);
                            break;
                        case R.id.action_profile:
                            Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                            startActivity(intentProfile);
                            break;
                    }
                    return true;
                });
    }
}