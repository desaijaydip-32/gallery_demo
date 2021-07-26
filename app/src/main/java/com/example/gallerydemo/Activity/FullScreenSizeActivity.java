package com.example.gallerydemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Adapter.EffectAdapter;
import com.example.gallerydemo.Adapter.FilterMenuAdapter;
import com.example.gallerydemo.Fragment.FileterFragment;
import com.example.gallerydemo.Fragment.ImageFragment;
import com.example.gallerydemo.Fragment.VideoFragment;
import com.example.gallerydemo.R;
import com.example.gallerydemo.databinding.ActivityFullScreenSizeBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mukesh.image_processing.ImageProcessor;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class FullScreenSizeActivity extends AppCompatActivity {

   ActivityFullScreenSizeBinding binding;
    private String path;
    public int img_icon[];
    public Bitmap[] filter_img;
    public String filtet_menu[];
    ImageView imageView;

    ImageView orignalTv, oneIV, twoIV, threeIV, fourIV, fiveIV, sixIV, sevenIV, eightIV, nineIV, tenIV, originalIV;
    Bitmap oneBitMap, twoBitMap, threeBitmap, fourBitMap, fiveBitMap, sixBitMap, sevenBitMap, eightBitMap, nineBitMap, tenBitMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullScreenSizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        path = getIntent().getStringExtra("imgpath");
//        postion = getIntent().getIntExtra("postion",0);
//        postion1= getIntent().getIntExtra("postion1",0);

        Glide.with(this).load(path).into(binding.recyclerview);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.share:
                                shareImages(path);
                                break;

                            case R.id.edit:
                                effectImg(path);
                                break;

                            case R.id.like_img:
                                Toast.makeText(FullScreenSizeActivity.this, "like", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.delete:
                                deletimage(path);
                                startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
                                break;

                            case R.id.details:
                                detailsImages(path);
                                break;
                            default:

                        }
                        return true;
                    }
                });

    }

    private void effectImg(String path) {

        binding.recyclerview12.setVisibility(View.VISIBLE);
        binding.bottomNavigation.setVisibility(View.GONE);
        binding.recyclerview1.setVisibility(View.VISIBLE);


        img_icon = new int[]{R.drawable.ic_filter, R.drawable.ic_text};
        filtet_menu = new String[]{"filter", "text"};
        imageView = findViewById(R.id.recyclerview);

        binding.recyclerview12.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview12.setAdapter(new FilterMenuAdapter(FullScreenSizeActivity.this, img_icon, filtet_menu));

//
        File imgFile = new File(path);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        AlertDialog dialog = builder.create();
//        View view = LayoutInflater.from(this).inflate(R.layout.bottomnavigatio_dialoge, null, false);


        ImageProcessor processor = new ImageProcessor();
//
//        oneIV = view.findViewById(R.id.idIVOne);
//        twoIV = view.findViewById(R.id.idIVTwo);
//        threeIV = view.findViewById(R.id.idIVThree);
//        fourIV = view.findViewById(R.id.idIVFour);
//        fiveIV = view.findViewById(R.id.idIVFive);
//
//        orignalTv = view.findViewById(R.id.idIVOriginalImage);

       // orignalTv.setImageBitmap(myBitmap);

        oneBitMap = processor.tintImage(myBitmap, 90);

        twoBitMap = processor.applySnowEffect(myBitmap);

        fourBitMap = processor.createSepiaToningEffect(myBitmap,1, 2, 1, 5);

        filter_img = new Bitmap[]{oneBitMap,twoBitMap, fourBitMap};
        binding.recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview1.setAdapter(new EffectAdapter(FullScreenSizeActivity.this,filter_img,imageView ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.images_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareImages(path);
                break;

            case R.id.delete:
                deletimage(path);
                startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
                break;

            case R.id.details:
                detailsImages(path);
                break;
            default:

        }
        return true;
    }

    private void detailsImages(String path) {
        TextView pathtxt, titletxt, sizetxt, datatxt;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.details_layout, null, false);
        sizetxt = view.findViewById(R.id.textView4);
        pathtxt = view.findViewById(R.id.textView5);
        titletxt = view.findViewById(R.id.textView6);
        datatxt = view.findViewById(R.id.textView8);

        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);


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
                datatxt.setText(dateString);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pathtxt.setText(dirPath);
        titletxt.setText(imageName);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


    }

//    private String formatchange(Long fileSizeInKB) {
//
//        return new DecimalFormat("#.##").format(fileSizeInKB);
//
//    }

    private void shareImages(String fileUri) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri imageUri = Uri.parse(fileUri);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(sharingIntent);
    }

    private void deletimage(String path) {

        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
                Toast.makeText(this, "Successfully Delet", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "delet not succesfully", Toast.LENGTH_SHORT).show();

            }
        }

    }


}