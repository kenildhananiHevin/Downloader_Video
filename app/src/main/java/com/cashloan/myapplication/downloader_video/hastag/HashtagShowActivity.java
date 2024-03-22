package com.cashloan.myapplication.downloader_video.hastag;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.downloader_video.BaseActivity;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.HashtagAdapter;
import com.cashloan.myapplication.downloader_video.adapter.HashtagShowAdapter;
import com.cashloan.myapplication.downloader_video.model.hashtag.Hashtag;
import com.cashloan.myapplication.downloader_video.model.hashtag.Root;
import com.cashloan.myapplication.downloader_video.model.hashtag.api.ApiService;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HashtagShowActivity extends BaseActivity {
    private static final String BASE_URL = "http://test.adstrunk.com/";
    private static final String TAG = "MainActivity";

    TextView hashtag_header,copy_text;
    ImageView back;
    CheckBox un_select;
    LinearLayout copy_click;
    RecyclerView hash_recycle;
    HashtagShowActivity activity;
    ArrayList<String> hashtagss = new ArrayList<>();
    HashtagShowAdapter hashtagShowAdapter;
    private boolean isAllSelected = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag_show);

        activity = this;

        String texts = getIntent().getStringExtra("name");

        hashtag_header = findViewById(R.id.hashtag_header);
        copy_text = findViewById(R.id.copy_text);
        un_select = findViewById(R.id.un_select);
        copy_click = findViewById(R.id.copy_click);
        hash_recycle = findViewById(R.id.hash_recycle);
        back = findViewById(R.id.back);

        hashtag_header.setSelected(true);
        copy_text.setSelected(true);
        hashtag_header.setText(texts);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Root> call = apiService.getHashtags(texts);

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root hashtagResponse = response.body();
                    if (hashtagResponse != null && hashtagResponse.getHashtags() != null) {
                        for (Hashtag hashtag : hashtagResponse.getHashtags()) {
                            hashtagss.add("#" + hashtag.getHashtag());
                        }
                        hash_recycle.setLayoutManager(new FlexboxLayoutManager(activity));
                        hashtagShowAdapter = new HashtagShowAdapter(activity,hashtagss);
                        hash_recycle.setAdapter(hashtagShowAdapter);
                    }
                } else {
                    Log.e(TAG, "Failed to get hashtags: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e(TAG, "Failed to get hashtags: " + t.getMessage());
            }
        });


        copy_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> selectedHashtags = hashtagShowAdapter.getSelectedHashtags();
                if (!selectedHashtags.isEmpty()) {
                    String hashtagsToShare = TextUtils.join(" ", selectedHashtags);
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Copied Text", hashtagsToShare);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(activity, getString(R.string.hashtag_copied), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, getString(R.string.select_hashtag), Toast.LENGTH_SHORT).show();
                }
            }
        });

        un_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un_select.setChecked(true);
                isAllSelected = !isAllSelected;
                if (isAllSelected) {
                    un_select.setChecked(true);
                    hashtagShowAdapter.selectAll();
                } else {
                    un_select.setChecked(false);
                    hashtagShowAdapter.clearSelection();
                }
            }
        });



       /* copy_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Hashtag Copy", hashtagss);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HashtagShowActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}