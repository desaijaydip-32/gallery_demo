package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.myapplication.Fragment.ActivitiesFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.InboxFragment;
import com.example.myapplication.Fragment.ProfileFragment;
import com.example.myapplication.Fragment.progressFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityFirst2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FirstActivity2 extends AppCompatActivity {


    ActivityFirst2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFirst2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomgationView.setBackground(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ActivitiesFragment()).addToBackStack("Back").commit();


        binding.bottomgationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Fragment temp = new Fragment();
                switch (item.getItemId()) {
                    case 0:
                        temp = new ActivitiesFragment();
                        break;
                    case 1:
                        temp = new InboxFragment();
                        break;
                    case 2:
                        temp = new HomeFragment();
                        break;
                    case 3:
                        temp = new progressFragment();
                        break;
                    case 4:
                        temp = new ProfileFragment();
                        break;
                }
                 getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public void onBackPressed() {

       finish();
    }


}