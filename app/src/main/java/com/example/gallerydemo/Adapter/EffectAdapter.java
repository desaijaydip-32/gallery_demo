package com.example.gallerydemo.Adapter;


import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;

import android.os.Parcelable;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallerydemo.Activity.FullScreenSizeActivity;
import com.example.gallerydemo.Activity.PhotosActivity;

import com.example.gallerydemo.Model.Model_images;
import com.example.gallerydemo.R;


import java.util.ArrayList;
import java.util.Locale;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.CustomFilter> {

    Context context;
    Bitmap[] filter_img;
    ImageView save_img;
    SaveImageBitmap saveImageBitmap;

    public ArrayList<Model_images> al_images = new ArrayList<>();


    public EffectAdapter(Context context, Bitmap[] filter_img, ImageView myBitmap) {
        this.context = context;
        this.filter_img = filter_img;
        this.save_img = myBitmap;
        this.saveImageBitmap = saveImageBitmap;
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

        save_img.setImageBitmap(filter_img[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FullScreenSizeActivity.class);
                intent.putExtra("Image", String.valueOf(save_img));
                context.startActivity(intent);
            }
        });
    }

    private void newAddFile(Bitmap bitmap) {

        try {
//
            Toast.makeText(context, "successfully stored", Toast.LENGTH_SHORT).show();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "img", "data");
            context.startActivity(new Intent(context, PhotosActivity.class));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addFavFolder(Bitmap bmap) {
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



    public interface SaveImageBitmap {

        public void saveImage(ImageView bitmap);
    }

}
