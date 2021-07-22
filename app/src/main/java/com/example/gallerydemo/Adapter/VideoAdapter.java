package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallerydemo.Model.VideoModel;
import com.example.gallerydemo.R;
import com.example.gallerydemo.VideoPlayActivity;

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
        holder.durationtextview.setText(videoArrayList.get(position).getVideoDuration());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class  CustomViewHolder extends RecyclerView.ViewHolder{

        TextView titletextview, durationtextview;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            titletextview= itemView.findViewById(R.id.title);
            durationtextview= itemView.findViewById(R.id.duration);
        }
    }
}
