package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.myapplication.databinding.ActivityFirst2Binding;


public class FirstActivity2 extends AppCompatActivity {


   ActivityFirst2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityFirst2Binding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
        binding.bottomgationView.setBackground(null);
    }
}