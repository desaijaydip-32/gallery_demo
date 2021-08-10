package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    private String mUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giritiscreen_activity);

        CustomSHarPrefrences customSHarPrefrences = new CustomSHarPrefrences(SplashActivity.this);

        //SharedPreferences preferences = getSharedPreferences("Demo", MODE_PRIVATE);

        // String  getApiToken = preferences.getString("token", "");

        Log.e("apitoken", customSHarPrefrences.getApi_toekn());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                if (customSHarPrefrences.getApi_toekn() != "")
                {
                    startActivity(new Intent(SplashActivity.this, FirstActivity2.class));
                    finish();

                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }


            }
        }, 1000);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}