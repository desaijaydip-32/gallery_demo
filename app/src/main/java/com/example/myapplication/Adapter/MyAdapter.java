package com.example.myapplication.Adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Fragment.Listcategoryfragment;
import com.example.myapplication.Model.event_list;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MyAdapter extends FragmentStatePagerAdapter {



    ArrayList<event_list> event = new ArrayList<>();

    public MyAdapter(@NonNull FragmentManager fm, ArrayList<event_list> eventlist)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.event=eventlist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return Listcategoryfragment.getInstantFragment(event.get(position));
    }

    @Override
    public int getCount()
    {
        return event.size();

    }
}