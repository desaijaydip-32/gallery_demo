package com.example.myapplication.Model;


public class EventDetail {
    int id;
    String event_title, start_time, end_time, location, description, media, event_rating, is_free, price, left, join;

    public EventDetail(int id, String event_title, String start_time, String end_time, String location, String description, String media, String event_rating, String is_free, String price, String left, String join) {
        this.id = id;
        this.event_title = event_title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.description = description;
        this.media = media;
        this.event_rating = event_rating;
        this.is_free = is_free;
        this.price = price;
        this.left = left;
        this.join = join;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getEvent_rating() {
        return event_rating;
    }

    public void setEvent_rating(String event_rating) {
        this.event_rating = event_rating;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

}