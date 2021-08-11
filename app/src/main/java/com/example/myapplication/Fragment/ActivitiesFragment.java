package com.example.myapplication.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Activities.CustomSHarPrefrences;
import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.Api;
import com.example.myapplication.Model.DataModal;
import com.example.myapplication.Model.event_list;
import com.example.myapplication.Model.listmodel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Apiclient;
import com.example.myapplication.Utils.AviLoader;
import com.example.myapplication.VerticalViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ActivitiesFragment extends Fragment {


    SharedPreferences shrd;

    AviLoader av;
    SharedPreferences right;
    CustomSHarPrefrences sHarPrefrences;


    MyAdapter mAdapter;
    VerticalViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         sHarPrefrences = new CustomSHarPrefrences(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        shrd = getContext().getSharedPreferences("userData", MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        String token=  sHarPrefrences.getApi_toekn();
        String id=  sHarPrefrences.getUser_id();

        av = new AviLoader(getContext());
        mPager = view.findViewById(R.id.verticleviewpager);

        av.show();
        Api retrofitAPI = Apiclient.getClient().create(Api.class);
        Call<listmodel> call = retrofitAPI.getlist(id, token, "0");



        call.enqueue(new Callback<listmodel>() {
            @Override
            public void onResponse(Call<listmodel> call, Response<listmodel> response) {

                if (response.isSuccessful()) {
                    av.dismiss();
                    listmodel list = response.body();
                    ArrayList<event_list>  eventlist = list.getEvent_list();

                    mAdapter = new MyAdapter(getActivity().getSupportFragmentManager(), eventlist);
                    mPager.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<listmodel> call, Throwable t) {
                Toast.makeText(getContext(),""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}