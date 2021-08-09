package com.example.myapplication.Model;

import android.text.Editable;

public class DataModal {

    String status;
    String message;
    String user_id;
    String api_token;


    USer_model uSer_model;


    public DataModal(String status, String message, String user_id, String api_token, USer_model uSer_model) {
        this.status = status;
        this.message = message;
        this.user_id = user_id;
        this.api_token = api_token;
        this.uSer_model = uSer_model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public USer_model getuSer_model() {
        return uSer_model;
    }

    public void setuSer_model(USer_model uSer_model) {
        this.uSer_model = uSer_model;
    }
}


