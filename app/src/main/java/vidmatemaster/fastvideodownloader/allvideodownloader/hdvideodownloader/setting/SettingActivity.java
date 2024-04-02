package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

import java.util.Objects;

import plugin.adsdk.service.AdsUtility;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.howtodownload.HowToDownloadActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.language.LanguageActivity;

public class SettingActivity extends BaseActivity {
    TextView setting_text, setting_language_text, setting_rate_click, setting_privacy_text, setting_share_text,setting_how_to_download_text;
    ImageView back, setting_next, rate_next, privacy_next, share_next,how_to_download_next;
    LinearLayout language_click, rate_click, privacy_click, share_click,how_to_download_click;
    SettingActivity activity;
    String languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nativeAdMedium();

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
        how_to_download_click = findViewById(R.id.how_to_download_click);
        setting_how_to_download_text = findViewById(R.id.setting_how_to_download_text);
        how_to_download_next = findViewById(R.id.how_to_download_next);

        setting_text.setSelected(true);
        setting_language_text.setSelected(true);
        setting_rate_click.setSelected(true);
        setting_privacy_text.setSelected(true);
        setting_share_text.setSelected(true);
        setting_how_to_download_text.setSelected(true);

        language_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, LanguageActivity.class));
            }
        });

        how_to_download_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, HowToDownloadActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        rate_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsUtility.rateUs(activity);
            }
        });

        share_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AdsUtility.shareApp(activity);
            }
        });

        privacy_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AdsUtility.privacyPolicy(activity);
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

    @Override
    public void onBackPressed() {
        backPressed();
    }
}