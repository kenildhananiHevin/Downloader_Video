package com.cashloan.myapplication.downloader_video.instagram;

import static com.cashloan.myapplication.downloader_video.fragment.instagram.InstagramFragment.getVideoFilenameFromURL;
import static com.cashloan.myapplication.downloader_video.fragment.instagram.InstagramFragment.startDownload;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.ObjectJson;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.facebook.other.DataDownloadHelper;
import com.cashloan.myapplication.downloader_video.facebook.other.FaceBookService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class InstaStoryVideoShowActivity extends BaseActivity {

    ImageView back, download, playPause;
    VideoView videoViewID;
    InstaStoryVideoShowActivity activity;
    DataDownloadHelper mediadownloadHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta_story_video_show);

        activity = this;
        mediadownloadHelper = new DataDownloadHelper(activity);




        back = findViewById(R.id.back);
        download = findViewById(R.id.download);
        videoViewID = findViewById(R.id.videoViewID);
        playPause = findViewById(R.id.playPause);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getIntent() != null) {
            try {
                videoViewID.setVideoURI(Uri.parse(getIntent().getStringExtra("video")));
                videoViewID.requestFocus();
                playPause.setVisibility(View.GONE);
                videoViewID.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String s = getIntent().getStringExtra("from");

        videoViewID.setOnCompletionListener(mp -> videoViewID.start());
        videoViewID.setOnTouchListener((v, event) -> {
            if (videoViewID.isPlaying()) {
                videoViewID.pause();
                playPause.setVisibility(View.VISIBLE);
                playPause.setImageResource(R.drawable.dw_play);
                return false;
            }
            playPause.setImageResource(R.drawable.dw_pause);
            videoViewID.start();
            playPause.setVisibility(View.GONE);
            return false;
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.equals("instagram")) {
                    startDownload(String.valueOf(Uri.parse(getIntent().getStringExtra("video"))), activity, getVideoFilenameFromURL(String.valueOf(Uri.parse(getIntent().getStringExtra("video")))));
                } else if (s.equals("facebook")) {
                    String videoFilename = "video" + new Random().nextInt() + ".mp4";
                    mediadownloadHelper.download(String.valueOf(Uri.parse(getIntent().getStringExtra("video"))),videoFilename,true);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoViewID.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playPause.setVisibility(View.GONE);
        videoViewID.start();
    }

    @Override
    public void onBackPressed() {
        videoViewID.pause();
        finish();
    }
}