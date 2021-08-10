package com.example.myapplication.Activities;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSHarPrefrences {

     private  String api_toekn, user_id;
     Context context;
     SharedPreferences sharedPreferences;
     SharedPreferences.Editor editor;
    public CustomSHarPrefrences(Context context) {
        this.context= context;
        sharedPreferences = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();

    }

    public String getApi_toekn() {
        return sharedPreferences.getString("token", "");
    }

    public void setApi_toekn(String api_toekn) {
        this.api_toekn = api_toekn;
        editor.putString("token", this.api_toekn).commit();
    }

    public String getUser_id() {
        return sharedPreferences.getString("id", "") ;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        editor.putString("id", this.user_id).commit();
    }
}
