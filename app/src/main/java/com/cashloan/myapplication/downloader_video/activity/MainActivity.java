package com.cashloan.myapplication.downloader_video.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.howtodownload.HowToDownloadActivity;
import com.cashloan.myapplication.downloader_video.language.LanguageActivity;
import com.cashloan.myapplication.downloader_video.setting.SettingActivity;
import com.cashloan.myapplication.downloader_video.webbrowser.WebBrowserActivity;

import java.util.Objects;

public class MainActivity extends BaseActivity {
    LinearLayout downloaded_click, web_browser_click, setting_click, how_to_click;
    MainActivity activity;
    String languageCode;
    ImageView language;
    TextView video_download, web_browser_text, setting_text, how_to_text, click_here_text;
    String[] permissions;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;

        SharedPreferences preferences = getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        downloaded_click = findViewById(R.id.downloaded_click);
        web_browser_click = findViewById(R.id.web_browser_click);
        setting_click = findViewById(R.id.setting_click);
        language = findViewById(R.id.languages);
        how_to_click = findViewById(R.id.how_to_click);
        video_download = findViewById(R.id.video_download);
        web_browser_text = findViewById(R.id.web_browser_text);
        setting_text = findViewById(R.id.setting_text);
        how_to_text = findViewById(R.id.how_to_text);
        click_here_text = findViewById(R.id.click_here_text);

        video_download.setSelected(true);
        web_browser_text.setSelected(true);
        setting_text.setSelected(true);
        how_to_text.setSelected(true);
        click_here_text.setSelected(true);

        downloaded_click.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissions = new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.READ_MEDIA_VIDEO
                };
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, permissions, 100);
                } else {
                    startActivity(new Intent(activity, VideoDownloaderActivity.class));
                }
            } else {
                permissions = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, permissions, 100);
                } else {
                    startActivity(new Intent(activity, VideoDownloaderActivity.class));
                }
            }
        });

        web_browser_click.setOnClickListener(v -> {
            startActivity(new Intent(activity, WebBrowserActivity.class));
        });

        setting_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, SettingActivity.class));
            }
        });


        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LanguageActivity.class);
                startActivity(intent);
            }
        });

        how_to_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, HowToDownloadActivity.class));
            }
        });
    }

    boolean backClick = false;


    @Override
    public void onBackPressed() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backClick = false;
            }
        }, 2000);
        if (backClick) {
            finishAffinity();
        } else {
            backClick = true;
            Toast.makeText(this, R.string.press, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        if (!Objects.equals(this.languageCode, languageCode)) {
            recreate();
        }
    }
}