package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.language;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import plugin.adsdk.service.BaseCallback;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.LanguageAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.intro.IntroActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.language_model.Languages;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils.LocaleHelper;

public class LanguageActivity extends BaseActivity {
    RecyclerView languageList;
    public static ImageView back;
    public static TextView imgDone;
    LanguageAdapter languageAdapter;
    ArrayList<Languages> languages = new ArrayList<>();
    boolean intent;
    boolean prefsStrings;
    boolean backClick = false;
    ProgressBar pbLoading;
    LanguageActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        nativeAdMedium();

        activity = this;

        SharedPreferences preferences = getSharedPreferences("Language", 0);
        String prefsString = preferences.getString("language_code", "en");
        intent = getIntent().getBooleanExtra("from", false);

        languageList = findViewById(R.id.language_list);
        imgDone = findViewById(R.id.img_done);
        pbLoading = findViewById(R.id.pbLoading);
        imgDone.setSelected(true);

        Handler handler = new Handler(Looper.getMainLooper());
        long delay = 1000L + new Random().nextInt(500);
        handler.postDelayed(() -> {
            pbLoading.setVisibility(View.GONE);
            languageList.setVisibility(View.VISIBLE);
        }, delay);


        languages.add(new Languages(R.drawable.english, "English", "en"));
        languages.add(new Languages(R.drawable.russian, "Russian", "ru"));
        languages.add(new Languages(R.drawable.french, "French", "fr"));
        languages.add(new Languages(R.drawable.greek, "Greek", "el"));
        languages.add(new Languages(R.drawable.german, "German", "de"));
        languages.add(new Languages(R.drawable.hindi, "Hindi", "hi"));
        languages.add(new Languages(R.drawable.indo, "Indonesian", "in"));
        languages.add(new Languages(R.drawable.italian, "Italian", "it"));
        languages.add(new Languages(R.drawable.japanese, "Japanese", "ja"));
        languages.add(new Languages(R.drawable.spanish, "Spanish", "es"));

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageAdapter.selected != -1) {
                            Languages languages = LanguageActivity.this.languages.get(languageAdapter.selected);
                            LocaleHelper.setLocale(LanguageActivity.this, languages.getLanguageCode());

                            SharedPreferences prefsString = getSharedPreferences("Language", 0);
                            SharedPreferences.Editor editor = prefsString.edit();

                            String languageCode = languages.getLanguageCode();
                            editor.putString("language_code", languageCode);
                            editor.putBoolean("language_set", true);
                            editor.apply();
                            if (intent) {
                                startActivity(new Intent(LanguageActivity.this, IntroActivity.class));
                                finish();
                            } else {
                                showInterstitial(new BaseCallback() {
                                    @Override
                                    public void completed() {
                                        finish();
                                    }
                                });
                            }

                } else {
                    Toast.makeText(LanguageActivity.this, R.string.select_language, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (intent) {
//            imgDone.setVisibility(View.GONE);
        } else {
            imgDone.setTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imgDone.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.language_bg)));
            }
        }

        languageList.setLayoutManager(new LinearLayoutManager(this));
        languageAdapter = new LanguageAdapter(activity, languages);
        languageList.setAdapter(languageAdapter);


        SharedPreferences preference = getSharedPreferences("Language", 0);
        prefsStrings = preference.getBoolean("language_set", false);
        if (prefsStrings) {
            for (int i = 0; i < languages.size(); i++) {
                if (Objects.equals(languages.get(i).getLanguageCode(), prefsString)) {
                    languageAdapter.selected = i;
                    languageAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (prefsStrings) {
            backPressed();
        } else {
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
                Toast.makeText(LanguageActivity.this, R.string.press, Toast.LENGTH_SHORT).show();
            }
        }
    }

}


