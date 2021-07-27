package com.example.gallerydemo.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gallerydemo.Activity.FullScreenSizeActivity;
import com.example.gallerydemo.Activity.PhotosActivity;
import com.example.gallerydemo.Fragment.ImageFragment;
import com.example.gallerydemo.Model.Model_images;
import com.example.gallerydemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.CustomFilter> {

    Context context;
    Bitmap[] filter_img;
    ImageView myBitmap;
    SaveImageBitmap saveImageBitmap;
    TextView saveTextView;

    public EffectAdapter(Context context, Bitmap[] filter_img, ImageView myBitmap, TextView saveTextView) {
        this.context = context;
        this.filter_img = filter_img;
        this.myBitmap = myBitmap;
        this.saveTextView = saveTextView;
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myBitmap.setImageBitmap(filter_img[position]);

                saveTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myBitmap.buildDrawingCache();
                        Bitmap bmap = myBitmap.getDrawingCache();

                      //  addFavFolder(bmap);


                       // String folderPath = Environment.getExternalStorageDirectory() + "/PestControl";
                       // File folder = new File(folderPath);

//                        File wallpaperDirectory = new File(folderPath);
//                        wallpaperDirectory.mkdirs();
//
//                        MediaStore.Images.Media.insertImage(context.getContentResolver(), bmap, "recent","ImageFragment.al_images");
//                        Toast.makeText(context, "successfully Saved", Toast.LENGTH_SHORT).show();
//
//                        context.startActivity(new Intent(context, PhotosActivity.class));
//
                    }
                });
            }
        });
    }

    public void addFavFolder(Bitmap bmap) {

        String resultPath =context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+
                "dirName" + System.currentTimeMillis() + ".jpg";
        Log.e("resultpath",resultPath);


        new File(resultPath).getParentFile().mkdir();

        if (Build.VERSION.SDK_INT < 29){

            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.TITLE, "Photo");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Edited");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
            values.put("_data", resultPath);

            ContentResolver cr = context.getContentResolver();
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            try {

                OutputStream fileOutputStream = new FileOutputStream(resultPath);
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                if(fileOutputStream != null){

                    Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }else
            {
            OutputStream fos = null;
            File file = new File(resultPath);

            final String relativeLocation = Environment.DIRECTORY_PICTURES;
            final ContentValues  contentValues = new ContentValues();

            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation+"/"+"dirName");
            contentValues.put(MediaStore.MediaColumns.TITLE, "Photo");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.DATE_TAKEN, System.currentTimeMillis ());
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
            contentValues.put(MediaStore.MediaColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
            contentValues.put(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));

            final ContentResolver resolver = context.getContentResolver();
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri = resolver.insert(contentUri, contentValues);

            try {
                fos = resolver.openOutputStream(uri);
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
            if(fos != null){
                Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public int getItemCount() {
        return filter_img.length;
    }


    class CustomFilter extends RecyclerView.ViewHolder {


        ImageView imageView;

        public CustomFilter(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imageView3);
        }
    }


    public interface SaveImageBitmap {

        public void saveImage(ImageView bitmap);
    }

}
