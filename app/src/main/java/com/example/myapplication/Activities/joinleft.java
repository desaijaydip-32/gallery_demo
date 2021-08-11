package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Api;
import com.example.myapplication.Model.DataModal;
import com.example.myapplication.Model.Event;
import com.example.myapplication.Model.EventDetail;
import com.example.myapplication.Model.listmodel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Apiclient;
import com.example.myapplication.Utils.AviLoader;
import com.example.myapplication.databinding.ActivityJoinleftBinding;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class joinleft extends AppCompatActivity {

    SharedPreferences shrd;
    AviLoader av;
    ActivityJoinleftBinding binding;
    CustomSHarPrefrences sHarPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinleftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        av = new AviLoader(joinleft.this);

        sHarPrefrences = new CustomSHarPrefrences(joinleft.this);

        String event_id = getIntent().getStringExtra("EVENT_ID");
        String token = sHarPrefrences.getApi_toekn();
        String id = sHarPrefrences.getUser_id();



//        Api retrofitAPI  =  Apiclient.getClient().create(Api.class);
//
//        Call<String> call = retrofitAPI.getEventDetails(id, token, event_id);
//
//         call.enqueue(new Callback<String>() {
//             @Override
//             public void onResponse(Call<String> call, Response<String> response) {
//
//                 String data = response.body();
//                 try {
//                     JSONObject jsonObject = new JSONObject(data);
//
//
//
//
//
//                 } catch (JSONException e) {
//                     e.printStackTrace();
//                 }
//
//             }
//
//             @Override
//             public void onFailure(Call<String> call, Throwable t) {
//
//             }
//         });


    }
}