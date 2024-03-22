package com.cashloan.myapplication.downloader_video.instagram;

import static com.cashloan.myapplication.downloader_video.fragment.instagram.InstagramFragment.getImageFilenameFromURL;
import static com.cashloan.myapplication.downloader_video.fragment.instagram.InstagramFragment.startDownload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.facebook.other.DataDownloadHelper;

import java.util.Random;

public class InstaStoryImageShowActivity extends BaseActivity {
    ImageView back, image, download;
    InstaStoryImageShowActivity activity;
    String image_path = "";
    String package_name = "";
    String type = "";
    DataDownloadHelper mediadownloadHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_story_image_show);

        activity = this;

        mediadownloadHelper = new DataDownloadHelper(activity);

        back = findViewById(R.id.back);
        image = findViewById(R.id.image);
        download = findViewById(R.id.download);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            image_path = intent.getStringExtra("image");
            type = intent.getStringExtra("type");
            package_name = intent.getStringExtra("pack");
            if (image_path != null) {
                Glide.with(activity).load(image_path).into(image);
            }
        }

        String s = getIntent().getStringExtra("from");

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.equals("instagram")) {
                    startDownload(image_path, activity, getImageFilenameFromURL(image_path));
                } else if (s.equals("facebook")) {
                    String imageFilename = "image" + new Random().nextInt() + ".jpg";
                    mediadownloadHelper.download(image_path, imageFilename, true);
                }

            }
        });

    }
}