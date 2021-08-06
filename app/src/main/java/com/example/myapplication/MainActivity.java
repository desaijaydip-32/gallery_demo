package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.myapplication.databinding.ActivityMainBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {




    String url = "http://www.idea2codeinfotech.com/gritim/api/v1/";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editEmail.getText().toString().equalsIgnoreCase("")) {
                    binding.editEmail.setError("Enter Valid Email");
                    binding.editEmail.setFocusable(true);
                } else if (binding.editPassword.getText().toString().equalsIgnoreCase("")) {
                    binding.editPassword.setError("Enter Valid Password");
                    binding.editPassword.setFocusable(true);
                } else {


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


                    api retrofitAPI = retrofit.create(api.class);
                    Call<DataModal> call = retrofitAPI.createPost(binding.editEmail.getText().toString(), binding.editPassword.getText().toString(), "1","a7bc894a2ddaf6baceecd693c6013104202db31e76fc87daa936d9e0d487c22c");

                    call.enqueue(new Callback<DataModal>() {
                        @Override
                        public void onResponse(Call<DataModal> call, Response<DataModal> response) {


                            Log.d("data", response.body().getApitokn().toString());

                             startActivity(new Intent(MainActivity.this, FirstActivity2.class));

                        }

                        @Override
                        public void onFailure(Call<DataModal> call, Throwable t) {

                        }
                    });







                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        binding.avi.setVisibility(View.GONE);
    }
}