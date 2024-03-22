package com.cashloan.myapplication.downloader_video.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.language.LanguageActivity;

import java.util.Objects;

public class SettingActivity extends BaseActivity {
    TextView setting_text, setting_language_text, setting_rate_click, setting_privacy_text, setting_share_text;
    ImageView back, setting_next, rate_next, privacy_next, share_next;
    LinearLayout language_click, rate_click, privacy_click, share_click;
    SettingActivity activity;
    String languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        activity = this;

        SharedPreferences preferences = getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        setting_text = findViewById(R.id.setting_text);
        back = findViewById(R.id.back);
        setting_language_text = findViewById(R.id.setting_language_text);
        setting_next = findViewById(R.id.setting_next);
        language_click = findViewById(R.id.language_click);
        rate_click = findViewById(R.id.rate_click);
        privacy_click = findViewById(R.id.privacy_click);
        share_click = findViewById(R.id.share_click);
        rate_next = findViewById(R.id.rate_next);
        privacy_next = findViewById(R.id.privacy_next);
        share_next = findViewById(R.id.share_next);
        setting_rate_click = findViewById(R.id.setting_rate_click);
        setting_privacy_text = findViewById(R.id.setting_privacy_text);
        setting_share_text = findViewById(R.id.setting_share_text);

        setting_text.setSelected(true);
        setting_language_text.setSelected(true);
        setting_rate_click.setSelected(true);
        setting_privacy_text.setSelected(true);
        setting_share_text.setSelected(true);

        setting_language_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, LanguageActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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