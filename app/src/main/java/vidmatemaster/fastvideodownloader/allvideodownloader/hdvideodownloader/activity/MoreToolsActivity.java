package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.hastag.HashtagActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.story.InstagramStoryActivity;

public class MoreToolsActivity extends BaseActivity {

    ImageView more_tools_back;
    TextView more_tools_text;
    MoreToolsActivity activity;
    RelativeLayout dp_layout, hashtag_layout, ig_layout;
    TextView dp_text, hashtag_text, ig_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);

        nativeAd();

        activity = this;

        more_tools_text = findViewById(R.id.more_tools_text);
        more_tools_back = findViewById(R.id.more_tools_back);
        dp_layout = findViewById(R.id.dp_layout);
        hashtag_layout = findViewById(R.id.hashtag_layout);
        ig_layout = findViewById(R.id.ig_layout);
        dp_text = findViewById(R.id.dp_text);
        hashtag_text = findViewById(R.id.hashtag_text);
        ig_text = findViewById(R.id.ig_text);

        more_tools_text.setSelected(true);
        dp_text.setSelected(true);
        hashtag_text.setSelected(true);
        ig_text.setSelected(true);

        more_tools_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }
}