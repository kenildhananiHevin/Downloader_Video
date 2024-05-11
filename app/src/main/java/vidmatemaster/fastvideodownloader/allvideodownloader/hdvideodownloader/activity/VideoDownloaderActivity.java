package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity;


import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.ItemAdapter;

public class VideoDownloaderActivity extends BaseActivity {
    VideoDownloaderActivity activity;
    RecyclerView item_recycle;
    ItemAdapter itemAdapter;
    ArrayList<Pair> tools_list = new ArrayList<>();
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_downloader);

        nativeAd();

        activity = this;

        item_recycle = findViewById(R.id.item_recycle);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tools_list.add(new Pair(R.drawable.eg_bg, getString(R.string.instagram)));
        tools_list.add(new Pair(R.drawable.fb_bg, getString(R.string.facebook)));
        tools_list.add(new Pair(R.drawable.wp_bg, getString(R.string.whatsapp)));
        tools_list.add(new Pair(R.drawable.more_t_bg, getString(R.string.more_tools)));

        item_recycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemAdapter = new ItemAdapter(this, tools_list);
        item_recycle.setAdapter(itemAdapter);
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}