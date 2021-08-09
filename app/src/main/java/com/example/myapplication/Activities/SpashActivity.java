package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class SpashActivity extends AppCompatActivity {


    private String mUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giritiscreen_activity);

        SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String  getApiToken = preferences.getString("token", "");


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(getApiToken.isEmpty()){
                    startActivity(new Intent(SpashActivity.this, LoginActivity.class));
                   finish();
                }
                else {
                    startActivity(new Intent(SpashActivity.this, FirstActivity2.class));
                    finish();
                }


            }
        }, 1000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}