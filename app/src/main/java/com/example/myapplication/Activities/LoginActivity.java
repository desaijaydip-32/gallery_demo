package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.myapplication.Api;
import com.example.myapplication.Model.DataModal;
import com.example.myapplication.Utils.Apiclient;
import com.example.myapplication.Utils.AviLoader;
import com.example.myapplication.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    static String apitpkon, user_id;
    AviLoader aviLoader;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private boolean saveLoing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        aviLoader = new AviLoader(LoginActivity.this);
        preferences = getSharedPreferences("UserData", MODE_PRIVATE);

          loginValue();

        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (binding.editEmail.getText().toString().equalsIgnoreCase("")) {
                    binding.editEmail.setError("Enter Valid Email");
                    binding.editEmail.setFocusable(true);

                } else if (binding.editPassword.getText().toString().equalsIgnoreCase("")) {
                    binding.editPassword.setError("Enter Valid Password");
                    binding.editPassword.setFocusable(true);

                }

                else {

                    aviLoader.show();

                    Api retrofitAPI = Apiclient.getClient().create(Api.class);
                    Call<DataModal> call = retrofitAPI.createPost(binding.editEmail.getText().toString(), binding.editPassword.getText().toString(), "1", "kjfhdkshfkjsdhffgdfg");


                    call.enqueue(new Callback<DataModal>() {
                        @Override
                        public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                            DataModal dataModal = response.body();

                            aviLoader.dismiss();
                            if (response.isSuccessful() && dataModal != null) {


                                String apitpkon = dataModal.getApi_token();
                                String user_id = dataModal.getUser_id();


                                editor = preferences.edit();
                                editor.putString("token", apitpkon);
                                editor.putString("id", user_id);
                                editor.apply();

                                if (binding.checkBox.isChecked()) {

                                    editor.putBoolean("saveLogin",true);
                                    editor.putString("Email",binding.editEmail.getText().toString());
                                    editor.putString("password",binding.editPassword.getText().toString());
                                    editor.apply();
                                }

                                else {
                                    editor.clear().commit();
                                }

                                if (dataModal.getStatus() == "0") {
                                    Toast.makeText(LoginActivity.this, dataModal.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(LoginActivity.this, FirstActivity2.class));
                                    Toast.makeText(LoginActivity.this, dataModal.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }


                        @Override
                        public void onFailure(Call<DataModal> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });


    }

    private void loginValue() {
        boolean savel= preferences.getBoolean("saveLogin", false);

        if(savel){

            binding.editEmail.setText(preferences.getString("Email",""));
            binding.editPassword.setText(preferences.getString("password",""));
        }
    }


}