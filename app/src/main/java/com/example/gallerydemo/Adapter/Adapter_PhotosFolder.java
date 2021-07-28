package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Activity.PhotosActivity;
import com.example.gallerydemo.Model.Model_images;
import com.example.gallerydemo.R;

import java.util.ArrayList;

public class Adapter_PhotosFolder extends RecyclerView.Adapter<Adapter_PhotosFolder.ViewHolder> {
    Context context;

    ArrayList<Model_images> al_menu = new ArrayList<>();


    public Adapter_PhotosFolder(Context context, ArrayList<Model_images> al_menu) {

        this.al_menu = al_menu;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_foldern.setText(al_menu.get(position).getStr_folder());
        holder.tv_foldersize.setText(al_menu.get(position).getAl_imagepath().size() + "");

        Glide.with(context).load(al_menu.get(position).getAl_imagepath().get(0))
                .into(holder.iv_image);

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PhotosActivity.class);
                intent.putExtra("value", al_menu.get(position).getAl_imagepath());
                intent.putExtra("postiton", position);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return al_menu.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_foldern = (TextView) itemView.findViewById(R.id.tv_folder);
            tv_foldersize = (TextView) itemView.findViewById(R.id.tv_foldersize);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }


}
