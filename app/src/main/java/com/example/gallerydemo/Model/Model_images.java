package com.example.gallerydemo.Model;

import android.widget.ImageView;

import java.util.ArrayList;

public class Model_images {
    String str_folder;
    ArrayList<String> al_imagepath;

   ImageView imageView;

    public Model_images(ImageView imageView) {

        this.imageView = imageView;
    }
    public Model_images() {
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<String> getAl_imagepath() {
        return al_imagepath;
    }

    public void setAl_imagepath(ArrayList<String> al_imagepath) {

        this.al_imagepath = al_imagepath;
    }
}
