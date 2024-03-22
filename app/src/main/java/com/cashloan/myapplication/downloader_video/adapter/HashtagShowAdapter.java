package com.cashloan.myapplication.downloader_video.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.hastag.HashtagShowActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HashtagShowAdapter extends RecyclerView.Adapter<HashtagShowAdapter.ViewHolder> {
    HashtagShowActivity activity;
    ArrayList<String> list;
    private HashSet<Integer> selectedItems = new HashSet<>();


    public HashtagShowAdapter(HashtagShowActivity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.hashtag_show_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagShowAdapter.ViewHolder holder, int position) {
        holder.hashtag_show_text.setText(list.get(position));

        holder.hashtag_show_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.contains(position)) {
                    selectedItems.remove(position);
                } else {
                    selectedItems.add(position);
                }
                notifyItemChanged(position);
            }
        });


        if (selectedItems.contains(position)) {
            holder.hashtag_show_text.setBackgroundResource(R.drawable.bg_hashtag_selected);
            holder.hashtag_show_text.setTextColor(activity.getColor(R.color.language_bg));
        } else {
            holder.hashtag_show_text.setBackgroundResource(R.drawable.language_click_bg);
            holder.hashtag_show_text.setTextColor(activity.getColor(R.color.black));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<String> getSelectedHashtags() {
        List<String> selectedHashtags = new ArrayList<>();
        for (int position : selectedItems) {
            selectedHashtags.add(list.get(position));
        }
        return selectedHashtags;
    }

    public void selectAll() {
        selectedItems.clear();
        for (int i = 0; i < getItemCount(); i++) {
            selectedItems.add(i);
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hashtag_show_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hashtag_show_text = itemView.findViewById(R.id.hashtag_show_text);
        }
    }
}
