package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerydemo.Model.VideoModel;
import com.example.gallerydemo.R;
import com.example.gallerydemo.Activity.VideoPlayActivity;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.CustomViewHolder> {

    Context context;
    ArrayList<VideoModel> videoArrayList;
   // public OnItemClickListener onItemClickListener;


    public VideoAdapter(Context context, ArrayList<VideoModel> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.video_list, parent, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.titletextview.setText(videoArrayList.get(position).getVideoTitle());

        String duration =videoArrayList.get(position).getVideoDuration();
        holder.durationtextview.setText(settimeDuration(Long.parseLong(duration)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });

        Glide.with(context).load(videoArrayList.get(position).getStr_thumb()).into(holder.thum_imgview);
    }

    private String settimeDuration(long duration) {
        StringBuffer buf = new StringBuffer();


        int minutes = (int) ((duration % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((duration % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();


    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class  CustomViewHolder extends RecyclerView.ViewHolder{

        TextView titletextview, durationtextview;
        ImageView thum_imgview;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            titletextview= itemView.findViewById(R.id.title);
            durationtextview= itemView.findViewById(R.id.duration);
            thum_imgview = itemView.findViewById(R.id.image_ic);
        }
    }
}
