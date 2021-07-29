package com.example.gallerydemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Adapter.EffectAdapter;
import com.example.gallerydemo.Adapter.FilterMenuAdapter;
import com.example.gallerydemo.R;
import com.example.gallerydemo.databinding.ActivityFullScreenSizeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mukesh.image_processing.ImageProcessor;

import java.io.File;
import java.io.IOException;


public class FullScreenSizeActivity extends AppCompatActivity {

    ActivityFullScreenSizeBinding binding;
    private String path1;
    public int img_icon[];
    public Bitmap[] filter_img;
    public String filtet_menu[];
    private ImageView imageView;
    boolean clicked = true;
    TextView cancelbtn, deletbtn;
    Bitmap oneBitMap, twoBitMap, threeBitmap, fourBitMap, fiveBitMap, sixBitMap, sevenBitMap, eightBitMap, nineBitMap, tenBitMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullScreenSizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        path1 = getIntent().getStringExtra("imgpath");


        Glide.with(this).load(path1).into(binding.recyclerview);

        binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    binding.imageView4.setImageResource(R.drawable.ic_ike);
                    try {

                        MediaStore.Images.Media.insertImage(getContentResolver(), path1, "img2", "data1");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
                } else {
                    binding.imageView4.setImageResource(R.drawable.ic_dislike);
                }
            }
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.share:
                                shareImages(path1);
                                break;

                            case R.id.edit:
                                effectImg(path1);
                                break;


                            case R.id.delete:
                                deletimage(path1);
                                break;

                            case R.id.details:
                                detailsImages(path1);
                                break;
                            default:

                        }
                        return true;
                    }
                });
    }


//    private void getStorageDir(String path) {
//
//        File file = new File(Environment.getExternalStorageDirectory() + "/folderName/folderName1");
//        if (!file.mkdirs()) {
//            file.mkdirs();
//        }
//
//        String filePath = file.getAbsolutePath() + File.separator + file;
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.MediaColumns.DATA, path);
//        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//    }

    private void effectImg(String path) {

        binding.recyclerview12.setVisibility(View.VISIBLE);
        binding.bottomNavigation.setVisibility(View.GONE);
        binding.recyclerview1.setVisibility(View.VISIBLE);
        binding.saveTextView.setVisibility(View.VISIBLE);
        binding.cancleImg.setVisibility(View.VISIBLE);

        img_icon = new int[]{R.drawable.ic_filter, R.drawable.ic_text};
        filtet_menu = new String[]{"filter", "text"};
        imageView = findViewById(R.id.recyclerview);

        binding.recyclerview12.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview12.setAdapter(new FilterMenuAdapter(FullScreenSizeActivity.this, img_icon, filtet_menu, new FilterMenuAdapter.PostitonPass() {
            @Override
            public void sendPostiton(int pos) {
                if (pos == 1) {
                    binding.editTextText.setVisibility(View.VISIBLE);

                    writeTextOnDrawable(path, binding.editTextText.getText().toString());
                } else {
                    binding.editTextText.setVisibility(View.GONE);
                }
            }
        }));


        File imgFile = new File(path);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ImageProcessor processor = new ImageProcessor();


        oneBitMap = processor.tintImage(myBitmap, 90);
        twoBitMap = processor.applySnowEffect(myBitmap);
        fourBitMap = processor.createSepiaToningEffect(myBitmap, 1, 2, 1, 5);


        filter_img = new Bitmap[]{oneBitMap, twoBitMap, fourBitMap};
        binding.recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview1.setAdapter(new EffectAdapter(FullScreenSizeActivity.this, filter_img, imageView, new EffectAdapter.SaveImageBitmap() {
            @Override
            public void saveImage(ImageView bitmap) {

                binding.saveTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bitmap.buildDrawingCache();
                        Bitmap bmap = imageView.getDrawingCache();
                        try {

                            MediaStore.Images.Media.insertImage(getContentResolver(), bmap, "img", "data");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(FullScreenSizeActivity.this, "Successfully Stored", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));

                    }
                });

            }
        }));


        binding.cancleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.saveTextView.setVisibility(View.INVISIBLE);
                binding.cancleImg.setVisibility(View.GONE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.recyclerview1.setVisibility(View.GONE);
                binding.recyclerview12.setVisibility(View.GONE);

            }
        });

    }

    private void writeTextOnDrawable(String path, String text) {
        File imgFile = new File(path);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), myBitmap, "img", "data");
            startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private float convertToPixels(Context context, int nDP) {
        final float conversionScale = context.getResources().getDisplayMetrics().density;
        return (int) ((nDP * conversionScale) + 0.5f);
    }


    private void detailsImages(String path) {
        TextView pathtxt, titletxt, sizetxt, datatxt, canceltxt;


        BottomSheetDialog dialog = new BottomSheetDialog(FullScreenSizeActivity.this, R.style.bottomsheetstle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = LayoutInflater.from(FullScreenSizeActivity.this).inflate(R.layout.details_layout, (ConstraintLayout) findViewById(R.id.contstrain_layout));

        sizetxt = view.findViewById(R.id.textView4);
        pathtxt = view.findViewById(R.id.textView5);
        titletxt = view.findViewById(R.id.textView6);
//        datatxt = view.findViewById(R.id.textView8);
        canceltxt = view.findViewById(R.id.cancletextView);


        dialog.setContentView(view);
        File file = new File(path);
        String dirPath = file.getAbsolutePath();
        String imageName = file.getName();


        long fileSizeInBytes = file.length();

        Double mSize = Double.parseDouble(fileSizeInBytes / 1024 + "");
        sizetxt.setText(String.format("%skb", mSize));

//        if (fileSizeInBytes < 1024) {
//
////            Long  fileSizeInKB =(fileSizeInBytes /1024) ;
//
//           // String dd =formatchange(fileSizeInKB);
//
//            sizetxt.setText(fileSizeInBytes+ "kb");
//
//        } else if (fileSizeInBytes > 1024) {
//
////            Long dumy= (fileSizeInBytes / 2058);
//
////            String ddx =formatchange(dumy);
//
//            sizetxt.setText(fileSizeInBytes+ "Mb");
//        }


        if (file.exists()) {
            ExifInterface intf;

            try {

                intf = new ExifInterface(path);
                String dateString = intf.getAttribute(ExifInterface.TAG_DATETIME);
                //datatxt.setText(dateString);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pathtxt.setText(dirPath);
        titletxt.setText(imageName);
        canceltxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private void shareImages(String fileUri) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri imageUri = Uri.parse(fileUri);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(sharingIntent);
    }

    private void deletimage(String path) {


        BottomSheetDialog dialog = new BottomSheetDialog(FullScreenSizeActivity.this, R.style.bottomsheetstle);

        View view = LayoutInflater.from(FullScreenSizeActivity.this).inflate(R.layout.delet_custom, (ConstraintLayout) findViewById(R.id.contstain));
        deletbtn = view.findViewById(R.id.button5);
        cancelbtn = view.findViewById(R.id.button4);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File fdelete = new File(path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        FullScreenSizeActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
                        Toast.makeText(FullScreenSizeActivity.this, "Successfully Delet", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));

                    } else {

                        Toast.makeText(FullScreenSizeActivity.this, "delet not succesfully", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setContentView(view);
        dialog.show();


    }


}