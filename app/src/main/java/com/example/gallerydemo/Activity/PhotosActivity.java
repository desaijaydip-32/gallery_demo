package com.example.gallerydemo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridView;

import com.example.gallerydemo.Adapter.ViewAdapter;

import com.example.gallerydemo.R;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        recyclerView = findViewById(R.id.gv_folder1);

        ArrayList<String> imageArray = getIntent().getStringArrayListExtra("value");
        ViewAdapter adapter = new ViewAdapter(this, imageArray);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}