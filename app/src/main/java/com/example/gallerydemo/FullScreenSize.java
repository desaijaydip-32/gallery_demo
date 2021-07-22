package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class FullScreenSize extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_size);
        imageView = findViewById(R.id.imageView);

        String path = getIntent().getStringExtra("imgpath");
        Glide.with(FullScreenSize.this).load(path) .into(imageView);


    }
}