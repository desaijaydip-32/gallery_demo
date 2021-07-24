package com.example.gallerydemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.gallerydemo.Adapter.GridViewAdapter;
import com.example.gallerydemo.Fragment.ImageFragment;
import com.example.gallerydemo.R;

public class PhotosActivity extends AppCompatActivity {

    int int_position;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        gridView = findViewById(R.id.gv_folder);


        int_position = getIntent().getIntExtra("value", 0);
        GridViewAdapter adapter = new GridViewAdapter(this, ImageFragment.al_images, int_position);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);

    }
}