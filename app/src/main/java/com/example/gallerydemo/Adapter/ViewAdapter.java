package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Activity.CropImageActivity;
import com.example.gallerydemo.R;

import java.util.ArrayList;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHoldercustom>{

    Context context;
    RecyclerView.ViewHolder viewHolder;
    ArrayList<String> al_menu;



    public ViewAdapter(Context context, ArrayList<String> al_menu) {

        this.al_menu = al_menu;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoldercustom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photo, parent, false);
        return new ViewHoldercustom(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldercustom holder, int position) {


        Glide.with(context)
                .load(al_menu.get(position))
                .into(holder.iv_image1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(context, MainActivity2.class);
                Intent intent = new Intent(context, CropImageActivity.class);
                intent.putExtra("imgpath", al_menu.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al_menu.size();
    }


      class ViewHoldercustom extends RecyclerView.ViewHolder {


        ImageView iv_image1;

        public ViewHoldercustom(@NonNull View itemView) {
            super(itemView);

            iv_image1= (ImageView) itemView.findViewById(R.id.iv_image);

        }


    }






}
