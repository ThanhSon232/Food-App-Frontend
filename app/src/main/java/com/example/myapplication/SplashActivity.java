package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPrefs = getPreferences(MODE_PRIVATE);
        System.out.println(mPrefs.contains("user"));
       if(getSharedPreferences("MYAPPLICATION",MODE_PRIVATE).contains("user")){
           Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
           startActivity(myIntent);
           finish();
       } else {
           Intent myIntent = new Intent(SplashActivity.this, LoginActivity.class);
           startActivity(myIntent);
           finish();
       }
    }
}