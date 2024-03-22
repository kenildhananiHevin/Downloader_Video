package com.cashloan.myapplication.downloader_video.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.hastag.HashtagActivity;
import com.cashloan.myapplication.downloader_video.hastag.HashtagShowActivity;
import com.cashloan.myapplication.downloader_video.model.hashtag.Tools;

import java.util.ArrayList;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.ViewHolder> {
    HashtagActivity hashtagActivity;
    ArrayList<Tools> list;

    public HashtagAdapter(HashtagActivity hashtagActivity, ArrayList<Tools> list) {
        this.hashtagActivity = hashtagActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(hashtagActivity).inflate(R.layout.hashtag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagAdapter.ViewHolder holder, int position) {
        Tools tools = list.get(position);
        holder.hashtag_text.setText(tools.getName());
        Glide.with(hashtagActivity).load(tools.getImage()).into(holder.hashtag_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(hashtagActivity, HashtagShowActivity.class);
                    intent.putExtra("name", tools.getName());
                    hashtagActivity.startActivity(intent);
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hashtag_image;
        TextView hashtag_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hashtag_image = itemView.findViewById(R.id.hashtag_image);
            hashtag_text = itemView.findViewById(R.id.hashtag_text);
        }
    }
}
