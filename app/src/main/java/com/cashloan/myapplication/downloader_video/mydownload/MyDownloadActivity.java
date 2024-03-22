package com.cashloan.myapplication.downloader_video.mydownload;

import static com.cashloan.myapplication.downloader_video.dpcreator.DpCreatorShowActivity.SaveVideoPathDirectory;
import static com.cashloan.myapplication.downloader_video.facebook.other.FaceBookService.mFaceBookVideoPathDirectory;
import static com.cashloan.myapplication.downloader_video.model.ConstMedia.RootDirectoryWhatsappShow;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.mEnstagramVideoPathDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.MyDownloadAdapter;
import com.cashloan.myapplication.downloader_video.instagram.FrameActivity;
import com.cashloan.myapplication.downloader_video.model.my_download.MyDownload;

import java.io.File;
import java.util.ArrayList;

public class MyDownloadActivity extends BaseActivity {

    TextView creation_header_text;
    MyDownloadActivity activity;
    ImageView back;
    ArrayList<File> listFile, wp_listFile, fb_listFile, dp_listFile;
    RecyclerView recycle_my_download;
    ArrayList<MyDownload> list = new ArrayList<>();
    MyDownloadAdapter myDownloadAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);

        activity = this;
        listFile = new ArrayList<>();
        wp_listFile = new ArrayList<>();
        fb_listFile = new ArrayList<>();
        dp_listFile = new ArrayList<>();

        creation_header_text = findViewById(R.id.creation_header_text);
        back = findViewById(R.id.back);
        recycle_my_download = findViewById(R.id.recycle_my_download);

     /*   insta_text.setSelected(true);
        insta_items.setSelected(true);*/


        if (mEnstagramVideoPathDirectory.listFiles() != null) {
            for (File file : mEnstagramVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    listFile.add(file);
                }
            }
        }

        if (RootDirectoryWhatsappShow.listFiles() != null) {
            for (File file : RootDirectoryWhatsappShow.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    wp_listFile.add(file);
                }
            }
        }

        if (mFaceBookVideoPathDirectory.listFiles() != null) {
            for (File file : mFaceBookVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    fb_listFile.add(file);
                }
            }
        }

        if (SaveVideoPathDirectory.listFiles() != null) {
            for (File file : SaveVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    dp_listFile.add(file);
                }
            }
        }

        if (!listFile.isEmpty()){
            list.add(new MyDownload("InstagramSaveVideo&Photos",listFile));
        }
        if (!wp_listFile.isEmpty()){
            list.add(new MyDownload("WhatsappSaver",wp_listFile));
        }
        if (!fb_listFile.isEmpty()){
            list.add(new MyDownload("FaceBookSavedVideo",fb_listFile));
        }
        if (!dp_listFile.isEmpty()){
            list.add(new MyDownload("SavePhotos", dp_listFile));
        }

        creation_header_text.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycle_my_download.setLayoutManager(new GridLayoutManager(activity,2));
        myDownloadAdapter = new MyDownloadAdapter(activity,list);
        recycle_my_download.setAdapter(myDownloadAdapter);
    }

}