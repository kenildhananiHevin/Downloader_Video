package com.cashloan.myapplication.downloader_video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.ItemAdapter;
import com.cashloan.myapplication.downloader_video.dpcreator.DpCreatorActivity;
import com.cashloan.myapplication.downloader_video.facebook.FaceBookActivity;
import com.cashloan.myapplication.downloader_video.hastag.HashtagActivity;
import com.cashloan.myapplication.downloader_video.instagram.InstagramActivity;
import com.cashloan.myapplication.downloader_video.instagram.InstagramStoryActivity;
import com.cashloan.myapplication.downloader_video.mydownload.MyDownloadActivity;
import com.cashloan.myapplication.downloader_video.setting.SettingActivity;
import com.cashloan.myapplication.downloader_video.whatsapp.MediaWpStatusActivity;

import java.util.ArrayList;

public class VideoDownloaderActivity extends BaseActivity {
    VideoDownloaderActivity activity;
    //    LinearLayout instagram_click, facebook_click, whatsapp_click, twitter_click;
    LinearLayout dp_layout, hashtag_layout, ig_layout,setting_layout,my_download_layout;
    TextView dp_text, hashtag_text, ig_text, video_download_text, more_tools,setting_text,my_download_text;
    RecyclerView item_recycle;
    ItemAdapter itemAdapter;
    ArrayList<Pair> tools_list = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_downloader);

        activity = this;

        dp_layout = findViewById(R.id.dp_layout);
        hashtag_layout = findViewById(R.id.hashtag_layout);
        ig_layout = findViewById(R.id.ig_layout);
        dp_text = findViewById(R.id.dp_text);
        hashtag_text = findViewById(R.id.hashtag_text);
        ig_text = findViewById(R.id.ig_text);
        item_recycle = findViewById(R.id.item_recycle);
        video_download_text = findViewById(R.id.video_download_text);
        back = findViewById(R.id.back);
        more_tools = findViewById(R.id.more_tools);
        setting_layout = findViewById(R.id.setting_layout);
        my_download_layout = findViewById(R.id.my_download_layout);
        setting_text = findViewById(R.id.setting_text);
        my_download_text = findViewById(R.id.my_download_text);

        dp_text.setSelected(true);
        hashtag_text.setSelected(true);
        ig_text.setSelected(true);
        video_download_text.setSelected(true);
        more_tools.setSelected(true);
        setting_text.setSelected(true);
        my_download_text.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, DpCreatorActivity.class));
            }
        });

        hashtag_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, HashtagActivity.class));
            }
        });

        ig_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, InstagramStoryActivity.class));
            }
        });

        setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, SettingActivity.class));
            }
        });

        my_download_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MyDownloadActivity.class));
            }
        });

        tools_list.add(new Pair(R.drawable.ig, getString(R.string.instagram)));
        tools_list.add(new Pair(R.drawable.fb, getString(R.string.facebook)));
        tools_list.add(new Pair(R.drawable.wp, getString(R.string.whatsapp)));

        item_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemAdapter = new ItemAdapter(this, tools_list);
        item_recycle.setAdapter(itemAdapter);
    }
}