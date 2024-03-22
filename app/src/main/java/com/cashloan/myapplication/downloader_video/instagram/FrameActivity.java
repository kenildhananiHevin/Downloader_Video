package com.cashloan.myapplication.downloader_video.instagram;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.facebook.FaceBookDownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.instagram.DownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.whatsapp.MediaDownloadFragment;

public class FrameActivity extends BaseActivity {

    TextView frame_header_text;
    ImageView back;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        String s = getIntent().getStringExtra("from");

        back = findViewById(R.id.back);
        frame_header_text = findViewById(R.id.frame_header_text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        frame_header_text.setSelected(true);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (s.equals("InstagramSaveVideo&Photos")) {
            DownloadFragment downloadFragment = new DownloadFragment();
            fragmentTransaction.replace(R.id.frame, downloadFragment);
            frame_header_text.setText("InstagramSaveVideo&Photos");
            fragmentTransaction.commit();
        } else if (s.equals("WhatsappSaver")) {
            MediaDownloadFragment mediaDownloadFragment = new MediaDownloadFragment();
            fragmentTransaction.replace(R.id.frame, mediaDownloadFragment);
            frame_header_text.setText("WhatsappSaver");
            fragmentTransaction.commit();
        } else if (s.equals("FaceBookSavedVideo")) {
            FaceBookDownloadFragment faceBookDownloadFragment = new FaceBookDownloadFragment();
            fragmentTransaction.replace(R.id.frame, faceBookDownloadFragment);
            frame_header_text.setText("FaceBookSavedVideo");
            fragmentTransaction.commit();
        }

    }
}