package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.hastag;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.HashtagAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.Tools;

import java.util.ArrayList;

public class HashtagActivity extends BaseActivity {

    ImageView back;
    TextView hashtag_header;
    RecyclerView recyclerView;
    ArrayList<Tools> list = new ArrayList<>();
    HashtagAdapter hashtagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

        hashtag_header = findViewById(R.id.hashtag_header);
        recyclerView = findViewById(R.id.tools_recycle);
        back = findViewById(R.id.back);

        list.add(new Tools(getString(R.string.social),R.drawable.hash_1,"Social"));
        list.add(new Tools(getString(R.string.family),R.drawable.hash_2,"Family"));
        list.add(new Tools(getString(R.string.celebration),R.drawable.hash_3,"Celebration"));
        list.add(new Tools(getString(R.string.holidays),R.drawable.hash_4,"Holiday"));
        list.add(new Tools(getString(R.string.food),R.drawable.hash_5,"Food"));
        list.add(new Tools(getString(R.string.art),R.drawable.hash_6,"Art"));
        list.add(new Tools(getString(R.string.nature),R.drawable.hash_7,"Nature"));
        list.add(new Tools(getString(R.string.weather),R.drawable.hash_8,"Animals"));
        list.add(new Tools(getString(R.string.animals),R.drawable.hash_9,"Weather"));
        list.add(new Tools(getString(R.string.celebrities),R.drawable.hash_10,"Celebrities"));

        hashtag_header.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hashtagAdapter = new HashtagAdapter(this,list);
        recyclerView.setAdapter(hashtagAdapter);
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}