package com.example.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.RcsUceAdapter;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {

    int int_position;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        gridView = findViewById(R.id.gv_folder);

        int_position = getIntent().getIntExtra("value", 0);
        GridViewAdapter  adapter = new GridViewAdapter(this,ImageFragment.al_images, int_position);
        gridView.setAdapter(adapter);

    }
}