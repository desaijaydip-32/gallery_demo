package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class event_list implements Parcelable {


      int  id;
      String event_title;
    String start_time;
    String end_time;
    String location;



    public static Creator<event_list> getCREATOR() {
        return CREATOR;
    }

    String description;
    String media;
    String event_rating;
    String is_free;
    String price;
    String total_join;
    String total_left;
    String join_limit;
    String status;
    String creat_art;

    public event_list(int id, String event_title, String start_time, String end_time, String location, String description, String media, String event_rating, String is_free, String price, String total_join, String total_left, String join_limit, String status, String creat_art) {
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
        this.total_join = total_join;
        this.total_left = total_left;
        this.join_limit = join_limit;
        this.status = status;
        this.creat_art = creat_art;
    }

    public event_list() {

    }


    protected event_list(Parcel in) {
        id = in.readInt();
        event_title = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        location = in.readString();
        description= in.readString();

        media = in.readString();
        event_rating = in.readString();
        is_free = in.readString();
        price = in.readString();
        total_join = in.readString();
        total_left = in.readString();
        join_limit = in.readString();
        status = in.readString();
        creat_art = in.readString();
    }

    public static final Creator<event_list> CREATOR = new Creator<event_list>() {
        @Override
        public event_list createFromParcel(Parcel in) {
            return new event_list(in);
        }

        @Override
        public event_list[] newArray(int size) {
            return new event_list[size];
        }
    };


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTotal_join() {
        return total_join;
    }

    public void setTotal_join(String total_join) {
        this.total_join = total_join;
    }

    public String getTotal_left() {
        return total_left;
    }

    public void setTotal_left(String total_left) {
        this.total_left = total_left;
    }

    public String getJoin_limit() {
        return join_limit;
    }

    public void setJoin_limit(String join_limit) {
        this.join_limit = join_limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreat_art() {
        return creat_art;
    }

    public void setCreat_art(String creat_art) {
        this.creat_art = creat_art;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(event_title);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(media);
        dest.writeString(event_rating);
        dest.writeString(is_free);
        dest.writeString(price);
        dest.writeString(total_join);
        dest.writeString(total_left);
        dest.writeString(join_limit);
        dest.writeString(status);
        dest.writeString(creat_art);
    }
}


