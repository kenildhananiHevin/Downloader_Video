package com.cashloan.myapplication.downloader_video.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.dpcreator.DpCreatorShowActivity;
import com.cashloan.myapplication.downloader_video.model.creator_model.DpCreator;

import java.util.ArrayList;

public class EditPicAdapter extends RecyclerView.Adapter<EditPicAdapter.ViewHolder> {
    DpCreatorShowActivity activity;
    ArrayList<DpCreator> list;

    public EditPicAdapter(DpCreatorShowActivity activity, ArrayList<DpCreator> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.creator_show_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EditPicAdapter.ViewHolder holder, int position) {
        DpCreator dpCreator = list.get(position);
        Glide.with(activity).load(dpCreator.getImage()).into(holder.frame_image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView frame_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frame_image = itemView.findViewById(R.id.frame_image);

        }
    }
}
