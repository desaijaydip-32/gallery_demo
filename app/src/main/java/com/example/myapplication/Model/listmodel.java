package com.example.myapplication.Model;

import java.util.ArrayList;

public class listmodel {

    ArrayList<event_list> event_list;
    public listmodel(ArrayList<com.example.myapplication.Model.event_list> event_list) {

        this.event_list = event_list;
    }

    public ArrayList<com.example.myapplication.Model.event_list> getEvent_list()
    {
        return event_list;
    }

    public void setEvent_list(ArrayList<com.example.myapplication.Model.event_list> event_list)
    {
        this.event_list = event_list;

    }
}
