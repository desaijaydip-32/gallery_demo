package com.example.myapplication;

import com.example.myapplication.Model.DataModal;
import com.example.myapplication.Model.listmodel;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("gritim/api/v1/login")
    Call<DataModal> createPost(@Field("email") String email,
                               @Field("password") String password,
                               @Field("device_type") String user_type,
                               @Field("device_token") String token);



    @FormUrlEncoded
    @POST("gritim/api/v1/listEvent?=princeee")
    Call<listmodel> getlist(@Field("user_id") String id,
                            @Field("api_token") String token,
                            @Field("page") String page);





}
