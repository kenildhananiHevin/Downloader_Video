package com.cashloan.myapplication.downloader_video.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.dpcreator.OpenGallery;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    OpenGallery activity;
    List<Pair<String, List<String>>> list;
    Click onClick;

    public AlbumAdapter(OpenGallery activity, List<Pair<String, List<String>>> list,Click onClick) {
        this.activity = activity;
        this.list = list;
        this.onClick = onClick;
    }

    public interface Click{
        void onClick(List<String> list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.album_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder holder, int position) {
        holder.album_text.setText(list.get(position).first);
        Glide.with(activity).load(list.get(position).second.get(0)).into(holder.image);
        holder.album_text.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(list.get(position).second);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView album_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            album_text = itemView.findViewById(R.id.album_text);
        }
    }
}
