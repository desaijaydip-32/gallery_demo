package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPref {


    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public SharedPref(Context context) {


        sp = context.getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        editor = sp.edit();
    }


    public String getUstoke() {

        return sp.getString("token", "");
    }

    public void setUstoke(String ustoke) {
        editor.putString("token", ustoke).commit();
    }

    public String getUseridl() {
        return sp.getString("uId", "");
    }

    public void setUseridl(String useridl) {
        editor.putString("uId", useridl).commit();
    }
}
