package com.example.myapplication;

import android.text.Editable;

public class DataModal {

    String  email,password,apitokn, userid;


    public DataModal(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getApitokn()
    {
        return apitokn;
    }

    public void setApitokn(String apitokn) {
        this.apitokn = apitokn;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


