package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.ItemAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.hastag.HashtagActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.story.InstagramStoryActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload.MyDownloadActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.setting.SettingActivity;

import java.util.ArrayList;
import java.util.Objects;

public class VideoDownloaderActivity extends BaseActivity {
    VideoDownloaderActivity activity;
    //    LinearLayout instagram_click, facebook_click, whatsapp_click, twitter_click;
    LinearLayout dp_layout, hashtag_layout, ig_layout,setting_layout,my_download_layout;
    TextView dp_text, hashtag_text, ig_text, video_download_text, more_tools,setting_text,my_download_text;
    RecyclerView item_recycle;
    ItemAdapter itemAdapter;
    ArrayList<Pair> tools_list = new ArrayList<>();
    ImageView back;
    String languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_downloader);

        nativeAd();

        activity = this;
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        dp_layout = findViewById(R.id.dp_layout);
        hashtag_layout = findViewById(R.id.hashtag_layout);
        ig_layout = findViewById(R.id.ig_layout);
        dp_text = findViewById(R.id.dp_text);
        hashtag_text = findViewById(R.id.hashtag_text);
        ig_text = findViewById(R.id.ig_text);
        item_recycle = findViewById(R.id.item_recycle);
        video_download_text = findViewById(R.id.video_download_text);
        back = findViewById(R.id.back);
        more_tools = findViewById(R.id.more_tools);
        setting_layout = findViewById(R.id.setting_layout);
        my_download_layout = findViewById(R.id.my_download_layout);
        setting_text = findViewById(R.id.setting_text);
        my_download_text = findViewById(R.id.my_download_text);

        dp_text.setSelected(true);
        hashtag_text.setSelected(true);
        ig_text.setSelected(true);
        video_download_text.setSelected(true);
        more_tools.setSelected(true);
        setting_text.setSelected(true);
        my_download_text.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        dp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, DpCreatorActivity.class));
            }
        });

        hashtag_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, HashtagActivity.class));
            }
        });

        ig_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, InstagramStoryActivity.class));
            }
        });

        setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial(new Intent(activity, SettingActivity.class));
            }
        });

        my_download_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MyDownloadActivity.class));
            }
        });

        tools_list.add(new Pair(R.drawable.ig, getString(R.string.instagram)));
        tools_list.add(new Pair(R.drawable.fb, getString(R.string.facebook)));
        tools_list.add(new Pair(R.drawable.wp, getString(R.string.whatsapp)));

        item_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemAdapter = new ItemAdapter(this, tools_list);
        item_recycle.setAdapter(itemAdapter);
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