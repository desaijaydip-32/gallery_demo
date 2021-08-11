package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.joinleft;
import  com.example.myapplication.Model.event_list;
import android.os.Parcelable;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentListcategoryfragmentBinding;

public class Listcategoryfragment extends Fragment {

    event_list event_list;
    private GestureDetector gdt;

    public static Fragment getInstantFragment(event_list event_list) {
        Listcategoryfragment fragment = new Listcategoryfragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event_list", (Parcelable) event_list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.event_list = getArguments().getParcelable("event_list");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentListcategoryfragmentBinding binding = FragmentListcategoryfragmentBinding.inflate(getLayoutInflater());

        binding.titletextView.setText(event_list.getEvent_title());
        binding.textView3.setText(event_list.getLocation());
        binding.eventRating.setText(event_list.getEvent_rating());


        if(event_list.getIs_free().equals("1"))
        {
            binding.price.setText("Free");
        }
        else {
            binding.price.setText(event_list.getPrice() + "$" + " USD");
        }

        binding.createdAt.setText(event_list.getStart_time());
        binding.description.setText(event_list.getDescription());

        Glide.with(getActivity())
                .load(event_list.getMedia()).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.loc_bg).into(binding.media);

        binding.media.setOnTouchListener(new OnSwipeTouchListener(getActivity()){
            @Override
            public void onSwipeLeft() {
                String eventid=""+event_list.getId();
                Intent intent = new Intent(getActivity(),joinleft.class);
                intent.putExtra("EVENT_ID",eventid);
                startActivity(intent, ActivityOptions.makeCustomAnimation(getActivity(),R.anim.enetr,R.anim.exit).toBundle());
            }
        });


//
//        binding.media.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
//
//            public void onSwipeLeft() {
//
//                String eventid=""+event_list.getId();
//
//                Intent intent = new Intent(getContext(),joinleft.class);
//                intent.putExtra("EVENT_ID",eventid);
//                startActivity(intent, ActivityOptions.makeCustomAnimation(getActivity(),R.anim.enetr,R.anim.exit).toBundle());
//
//                Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
//            }

//
//        });


        return binding.getRoot();
    }
}