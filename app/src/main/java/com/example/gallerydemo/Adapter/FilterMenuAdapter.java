package com.example.gallerydemo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gallerydemo.R;

public class FilterMenuAdapter extends RecyclerView.Adapter<FilterMenuAdapter.CustomHolder> {

    Context context;
    int[] img_icon;
    String[] filtet_menu;


    public FilterMenuAdapter(Context context, int[] img_icon, String[] filtet_menu) {
        this.context = context;
        this.img_icon = img_icon;
        this.filtet_menu = filtet_menu;

    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_menu_custom, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.imageView.setImageResource(img_icon[position]);
        holder.name_text.setText(filtet_menu[position]);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Log.e("postion", String.valueOf(position));
           }
       });


    }

    @Override
    public int getItemCount() {
        return img_icon.length;
    }

    class CustomHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name_text;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            name_text = itemView.findViewById(R.id.menuTextView);
        }
    }

    public interface PostitonSend {

        public void sendPostiton(int pos);
    }

}
