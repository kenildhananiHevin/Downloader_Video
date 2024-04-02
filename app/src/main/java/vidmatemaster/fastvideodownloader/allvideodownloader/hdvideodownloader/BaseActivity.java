package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils.LocaleHelper;

public class BaseActivity extends plugin.adsdk.service.BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        handleLanguageChange();
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String prefsString = preferences.getString("language_code", "en");
        LocaleHelper.setLocale(BaseActivity.this, prefsString);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleLanguageChange();
    }

    protected void handleLanguageChange() {
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String languageCode = preferences.getString("language_code", "en");
        LocaleHelper.setLocale(BaseActivity.this, languageCode);
    }
}
