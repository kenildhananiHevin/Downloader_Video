package com.cashloan.myapplication.downloader_video.hastag;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.HashtagAdapter;
import com.cashloan.myapplication.downloader_video.model.hashtag.Tools;

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

        list.add(new Tools(getString(R.string.social),R.drawable.hash_1));
        list.add(new Tools(getString(R.string.family),R.drawable.hash_2));
        list.add(new Tools(getString(R.string.celebration),R.drawable.hash_3));
        list.add(new Tools(getString(R.string.holidays),R.drawable.hash_4));
        list.add(new Tools(getString(R.string.food),R.drawable.hash_5));
        list.add(new Tools(getString(R.string.art),R.drawable.hash_6));
        list.add(new Tools(getString(R.string.nature),R.drawable.hash_7));
        list.add(new Tools(getString(R.string.weather),R.drawable.hash_8));
        list.add(new Tools(getString(R.string.animals),R.drawable.hash_9));
        list.add(new Tools(getString(R.string.celebrities),R.drawable.hash_10));

        hashtag_header.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hashtagAdapter = new HashtagAdapter(this,list);
        recyclerView.setAdapter(hashtagAdapter);
    }
}