package com.example.gallerydemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gallerydemo.Fragment.ImageFragment;
import com.example.gallerydemo.Fragment.VideoFragment;
import com.example.gallerydemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new ImageFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment temp = null;
                        switch (item.getItemId()) {
                            case R.id.imgfolder:
                                temp = new ImageFragment();
                                break;
                            case R.id.video:
                                temp = new VideoFragment();
                                break;

                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, temp).commit();

                        return true;
                    }
                });


    }
}
