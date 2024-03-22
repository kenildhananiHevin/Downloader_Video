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
import com.cashloan.myapplication.downloader_video.model.web_model.WebBrowser;
import com.cashloan.myapplication.downloader_video.webbrowser.WebBrowserActivity;
import com.cashloan.myapplication.downloader_video.webbrowser.WebBrowserShowActivity;

import java.util.ArrayList;

public class WebBrowserAdapter extends RecyclerView.Adapter<WebBrowserAdapter.ViewHolder> {
    WebBrowserActivity activity;
    ArrayList<WebBrowser> list;

    public WebBrowserAdapter(WebBrowserActivity activity, ArrayList<WebBrowser> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.web_browser_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WebBrowserAdapter.ViewHolder holder, int position) {
        WebBrowser webBrowser = list.get(position);
        holder.browser_text.setText(webBrowser.getName());
        holder.browser_text.setSelected(true);
        Glide.with(activity).load(webBrowser.getImage()).into(holder.browser_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebBrowserShowActivity.class);
                intent.putExtra("url", webBrowser.getUrl());
                activity.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView browser_image;
        TextView browser_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            browser_image = itemView.findViewById(R.id.browser_image);
            browser_text = itemView.findViewById(R.id.browser_text);
        }
    }
}
