package com.example.myapplication;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface api {

    @FormUrlEncoded
    @POST("login")
    Call<DataModal> createPost(@Field("email") String email, @Field("password") String password, @Field("device_type") String user_type, @Field("device_token") String token);


}
