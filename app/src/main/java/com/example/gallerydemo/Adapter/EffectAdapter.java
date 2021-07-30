package com.example.gallerydemo.Adapter;


import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;


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


public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.CustomFilter> {

    Context context;
    Bitmap[] filter_img;
    ImageView save_img;
    String[] picName;
    boolean clicked = true;
    SaveImageBitmap saveImageBitmap;

    public ArrayList<Model_images> al_images = new ArrayList<>();


    public EffectAdapter(Context context, Bitmap[] filter_img, ImageView myBitmap, String[] picName, SaveImageBitmap saveImageBitmap) {
        this.context = context;
        this.filter_img = filter_img;
        this.save_img = myBitmap;
        this.picName = picName;
        this.saveImageBitmap = saveImageBitmap;
    }

    @NonNull
    @Override
    public CustomFilter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_img, null, false);
        return new CustomFilter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomFilter holder, int position) {
        holder.imageView.setImageBitmap(filter_img[position]);
        holder.pickName.setText(picName[position]);

        if (clicked) {
            holder.imageView.setBackgroundResource(R.drawable.selected);
        } else {


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_img.setImageBitmap(filter_img[position]);
                saveImageBitmap.saveImage(save_img);

                notifyDataSetChanged();

//                Intent intent = new Intent(context, FullScreenSizeActivity.class);
//                intent.putExtra("Image", String.valueOf(save_img));
//                context.startActivity(intent);
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


    @Override
    public int getItemCount() {
        return filter_img.length;
    }


    class CustomFilter extends RecyclerView.ViewHolder {
        ImageView imageView, select_img;
        TextView pickName;


        public CustomFilter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
            pickName = itemView.findViewById(R.id.textView2);

            //  select_img = itemView.findViewById(R.id.imageView8);
        }
    }


    public interface SaveImageBitmap {

        public void saveImage(ImageView bitmap);
    }

}
