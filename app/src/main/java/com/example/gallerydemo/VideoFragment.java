package com.example.gallerydemo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gallerydemo.Adapter.VideoAdapter;
import com.example.gallerydemo.Model.VideoModel;

import java.util.ArrayList;


public class VideoFragment extends Fragment {
    public static ArrayList<VideoModel> videoArrayList;

    public static final int PERMISSION_READ = 0;

  RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  view=  inflater.inflate(R.layout.fragment_video2, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        videoArrayList= new ArrayList<>();


        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);

        } else {
            Log.e("Else", "Else");
            fn_imagespath();
        }
        return  view;
    }

    private void fn_imagespath() {

        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
       // Log.e("file==", cursor.toString());

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                VideoModel  videoModel  = new VideoModel();
                videoModel .setVideoTitle(title);
                videoModel .setVideoUri(Uri.parse(data));
               // videoModel .setVideoDuration(timeConversion(Long.parseLong(duration)));
                videoArrayList.add(videoModel);


            } while (cursor.moveToNext());
        }

//        Log.e("Video==",videoArrayList.toString());
        VideoAdapter  adapter = new VideoAdapter(getContext(), videoArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            fn_imagespath();
        }
        else {
            Toast.makeText(getContext(), "permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private String timeConversion(long parseLong) {
        String videoTime;
        int dur = (int) parseLong;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }


}