package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.hastag;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.HashtagShowAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.Hashtag;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.Root;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.hashtag.api.ApiService;

public class HashtagShowActivity extends BaseActivity {
    private static final String BASE_URL = "http://test.adstrunk.com/";
    private static final String TAG = "MainActivity";

    TextView hashtag_header, copy_text, share_text, network_on;
    ImageView back, retery_network;
    public static CheckBox un_select;
    LinearLayout copy_click, share_click;
    RecyclerView hash_recycle;
    HashtagShowActivity activity;
    ArrayList<String> hashtagss = new ArrayList<>();
    HashtagShowAdapter hashtagShowAdapter;
    private boolean isAllSelected = false;
    ProgressBar progressBar;
    LinearLayout bottom_layout, network;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag_show);

        activity = this;

        String texts = getIntent().getStringExtra("name");
        String text = getIntent().getStringExtra("nameId");

        hashtag_header = findViewById(R.id.hashtag_header);
        copy_text = findViewById(R.id.copy_text);
        un_select = findViewById(R.id.un_select);
        copy_click = findViewById(R.id.copy_click);
        hash_recycle = findViewById(R.id.hash_recycle);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progress);
        share_click = findViewById(R.id.share_click);
        share_text = findViewById(R.id.share_text);
        bottom_layout = findViewById(R.id.bottom_layout);
        network_on = findViewById(R.id.network_on);
        retery_network = findViewById(R.id.retery_network);
        network = findViewById(R.id.network);
        hashtag_header.setSelected(true);
        copy_text.setSelected(true);
        share_text.setSelected(true);
        hashtag_header.setText(texts);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        if (!isNetworkAvailable(HashtagShowActivity.this)) {
            network.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        ApiCall(text);


        network_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                ApiCall(text);
                progressBar.setVisibility(View.VISIBLE);
            }
        });


        copy_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(HashtagShowActivity.this)) {
                    List<String> selectedHashtags = hashtagShowAdapter.getSelectedHashtags();
                    if (selectedHashtags != null && !selectedHashtags.isEmpty()) {
                        String hashtagsToShare = TextUtils.join(" ", selectedHashtags);
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Copied Text", hashtagsToShare);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(activity, getString(R.string.hashtag_copied), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, getString(R.string.select_hashtag), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.please_turn_on_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });

        un_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(HashtagShowActivity.this)) {
                    un_select.setChecked(true);
                    isAllSelected = !isAllSelected;
                    if (isAllSelected) {
                        un_select.setChecked(true);
                        hashtagShowAdapter.selectAll();
                    } else {
                        un_select.setChecked(false);
                        hashtagShowAdapter.clearSelection();
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.please_turn_on_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        share_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(HashtagShowActivity.this)) {
                    List<String> selectedHashtags = hashtagShowAdapter.getSelectedHashtags();
                    if (selectedHashtags != null && !selectedHashtags.isEmpty()) {
                        String hashtagsToShare = TextUtils.join(" ", selectedHashtags);
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, hashtagsToShare);
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    } else {
                        Toast.makeText(activity, getString(R.string.select_hashtag), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.please_turn_on_internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void ApiCall(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Root> call = apiService.getHashtags(text);

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root hashtagResponse = response.body();
                    if (hashtagResponse != null && hashtagResponse.getHashtags() != null) {
                        for (Hashtag hashtag : hashtagResponse.getHashtags()) {
                            hashtagss.add("#" + hashtag.getHashtag());
                            Log.d(TAG, "onResponsesssssss: " + hashtag.getHashtag());
                        }
                        progressBar.setVisibility(View.GONE);
                        hash_recycle.setLayoutManager(new FlexboxLayoutManager(activity));
                        hashtagShowAdapter = new HashtagShowAdapter(activity, hashtagss);
                        hash_recycle.setAdapter(hashtagShowAdapter);
                        un_select.setVisibility(View.VISIBLE);
                        bottom_layout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e(TAG, "Failed to get hashtags: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e(TAG, "Failed to get hashtags: " + t.getMessage());
                network.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}