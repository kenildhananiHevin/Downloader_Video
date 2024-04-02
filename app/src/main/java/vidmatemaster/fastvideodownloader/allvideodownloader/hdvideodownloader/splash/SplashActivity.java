package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.splash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity.MainActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.language.LanguageActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils.LocaleHelper;

import plugin.adsdk.extras.BaseLauncherActivity;

public class SplashActivity extends BaseLauncherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleLanguageChange();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Intent destinationIntent() {
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        boolean prefsString = preferences.getBoolean("language_set", false);
        if (prefsString) {
            return new Intent(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            return new Intent(new Intent(SplashActivity.this, LanguageActivity.class).putExtra("from", true));
        }
    }

    @Override
    protected String extraAppContentText() {
         return getString(plugin.adsdk.R.string.app_content);
    }

    @Override
    protected int extraAppContentImage() {
        return R.mipmap.ic_launcher;
    }

    public static final String BASE_URL = "https://ht.askforad.com/";

    @Override
    protected String baseURL() {
        return BASE_URL;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleLanguageChange();
    }

    public SplashActivity() {
        super(R.layout.activity_splash, plugin.adsdk.R.layout.ad_activity_extra_dashboard);
    }

    protected void handleLanguageChange() {
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        LocaleHelper.setLocale(SplashActivity.this, languageCode);
    }
}