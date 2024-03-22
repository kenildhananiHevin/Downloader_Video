package com.cashloan.myapplication.downloader_video.whatsapp;

import static com.cashloan.myapplication.downloader_video.other.CommonClass.REQUEST_PERM_DELETE;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.showToast;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.cashloan.myapplication.downloader_video.BaseActivity;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.mydownload.SaveImageListAdapter;
import com.cashloan.myapplication.downloader_video.other.CommonClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaWpVIdeoViewerActivity extends BaseActivity implements SaveImageListAdapter.DeleteData {
    VideoView videoViewID;
    ImageView playPause,imgBack,share,delete;
    TextView txtTitle;
    SaveImageListAdapter.DeleteData deleteData;
    MediaWpVIdeoViewerActivity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity_whatz_video_viewer);

        deleteData = this;
        activity = this;

        videoViewID = findViewById(R.id.videoViewID);
        playPause = findViewById(R.id.playPause);
        imgBack = findViewById(R.id.imgBack);
        txtTitle = findViewById(R.id.txtTitle);
        share = findViewById(R.id.share);
        delete = findViewById(R.id.delete);

        txtTitle.setSelected(true);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String s = getIntent().getStringExtra("name");
        txtTitle.setText(s);

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

        String wp_s = getIntent().getStringExtra("from");

        if (wp_s != null && wp_s.equals("whatsapp")) {
            share.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareVideo(activity, String.valueOf(Uri.parse(getIntent().getStringExtra("video"))));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog delete_dialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.dialog_delete, null);
                delete_dialog.setView(view1);
                delete_dialog.setCanceledOnTouchOutside(false);
                TextView cancel = view1.findViewById(R.id.delete_cancel);
                TextView delete_btn = view1.findViewById(R.id.delete_ok);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData.deleteclick(new File(Uri.parse(getIntent().getStringExtra("video")).toString()), 0);
                        delete_dialog.dismiss();
                    }
                });

                delete_dialog.show();
                Window window = delete_dialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.80);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
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

    @Override
    public void deleteclick(File str, int i) {
        if (Build.VERSION.SDK_INT >= 30) {
            Intent b = new Intent();
            b.putExtra("pos", i);
            b.putExtra("flag", true);
            List<File> list = new ArrayList<>();
            list.add(str);
            CommonClass.deleteFiles(list, REQUEST_PERM_DELETE, activity, b);
        } else {
            File file = str;
            if (file.exists()) {
                if (file.delete()) {
                    showToast(activity, getString(R.string.fileDeletedSuccessfully));
                    return;
                }
                showToast(activity, getString(R.string.fileNotDeleted));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERM_DELETE && resultCode == -1) {
            finish();
        }
    }
}
