package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class gritispashscreen extends AppCompatActivity {


    private String mUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giritiscreen_activity);

        SharedPref sharedPref = new SharedPref(gritispashscreen.this);
        mUserToken = sharedPref.getUstoke();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (mUserToken != null) {

                    startActivity(new Intent(gritispashscreen.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(gritispashscreen.this, gritispashscreen.class));
                    finish();
                }

                finish();
            }
        }, 1000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}