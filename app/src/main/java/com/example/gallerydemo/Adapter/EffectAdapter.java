package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gallerydemo.Activity.FullScreenSizeActivity;
import com.example.gallerydemo.R;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.CustomFilter> {

    Context context;
    Bitmap[] filter_img;
    ImageView myBitmap;

    public EffectAdapter(Context context, Bitmap[] filter_img, ImageView myBitmap) {
        this.context = context;
        this.filter_img = filter_img;
        this.myBitmap = myBitmap;
    }

    @NonNull
    @Override
    public CustomFilter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_img, parent, false);
        return new CustomFilter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomFilter holder, int position) {
        holder.imageView.setImageBitmap(filter_img[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myBitmap.setImageBitmap(filter_img[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filter_img.length;
    }


    class CustomFilter extends RecyclerView.ViewHolder {


        ImageView imageView;

        public CustomFilter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }


}
