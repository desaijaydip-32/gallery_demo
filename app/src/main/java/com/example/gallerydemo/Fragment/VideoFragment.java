package com.example.gallerydemo.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.gallerydemo.R;

import java.time.Period;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class VideoFragment extends Fragment {
    public static ArrayList<VideoModel> videoArrayList;

    public static final int PERMISSION_READ = 0;

    RecyclerView recyclerView;
    Formatter mFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_video2, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        videoArrayList = new ArrayList<>();

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
        return view;
    }

    private void fn_imagespath() {

        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);


        if (cursor != null && cursor.moveToFirst())
        {
            do
                {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));

                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                int thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

                VideoModel videoModel = new VideoModel();
                videoModel.setVideoTitle(title);
                videoModel.setVideoUri(Uri.parse(data));
                videoModel.setStr_thumb(cursor.getString(thum));
                videoModel.setVideoDuration(duration);
                videoArrayList.add(videoModel);



            } while (cursor.moveToNext());
        }



        VideoAdapter adapter = new VideoAdapter(getContext(), videoArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            fn_imagespath();
        } else {
            Toast.makeText(getContext(), "permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private String timeConversion(long parseLong) {
        StringBuffer buf = new StringBuffer();
        int hours = (int) (parseLong / (1000 * 60 * 60));
        int minutes = (int) ((parseLong % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((parseLong % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


}