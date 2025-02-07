package com.cashloan.myapplication.downloader_video.splash;


import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.activity.MainActivity;
import com.cashloan.myapplication.downloader_video.language.LanguageActivity;
import com.cashloan.myapplication.downloader_video.utils.LocaleHelper;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("Language", 0);
                boolean prefsString = preferences.getBoolean("language_set", false);
                if (prefsString) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LanguageActivity.class).putExtra("from", true));
                }
            }
        },2000);
    }
}