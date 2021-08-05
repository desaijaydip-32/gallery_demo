package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class gritispashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         Timer  timer = new Timer();
         timer.schedule(new TimerTask() {
             @Override
             public void run() {


                 startActivity(new Intent(gritispashscreen.this, MainActivity.class));
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