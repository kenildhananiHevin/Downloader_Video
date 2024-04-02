package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload.MyDownloadActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.setting.SettingActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.webbrowser.WebBrowserActivity;

import java.util.Objects;


public class MainActivity extends BaseActivity {
    LinearLayout video_download_layout, web_layout, my_download_layout;
    MainActivity activity;
    String languageCode;
    ImageView setting;
    TextView download_text, web_text, my_download_text, video_download_text;
    String[] permissions;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nativeAd();
        activity = this;
        SharedPreferences preferences = getSharedPreferences("Language", 0);
        languageCode = preferences.getString("language_code", "en");

        video_download_layout = findViewById(R.id.video_download_layout);
        web_layout = findViewById(R.id.web_layout);
        my_download_layout = findViewById(R.id.my_download_layout);
        video_download_text = findViewById(R.id.video_download_text);

        setting = findViewById(R.id.setting);
        download_text = findViewById(R.id.download_text);
        web_text = findViewById(R.id.web_text);
        my_download_text = findViewById(R.id.my_download_text);

        download_text.setSelected(true);
        web_text.setSelected(true);
        my_download_text.setSelected(true);
        video_download_text.setSelected(true);

        video_download_layout.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissions = new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.POST_NOTIFICATIONS,
                        Manifest.permission.READ_MEDIA_VIDEO
                };
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_VIDEO)) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(activity, permissions, 100);
                    }
                } else {
                    showInterstitial(new Intent(activity, VideoDownloaderActivity.class));
                }
            } else {
                permissions = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(activity, permissions, 100);
                    }
                } else {
                    showInterstitial(new Intent(activity, VideoDownloaderActivity.class));
                }
            }
        });

        web_layout.setOnClickListener(v -> {
            showInterstitial(new Intent(activity, WebBrowserActivity.class));
        });

        my_download_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial(new Intent(activity, MyDownloadActivity.class));
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial(new Intent(activity, SettingActivity.class));
            }
        });
    }

    boolean backClick = false;

    @Override
    public void onBackPressed() {
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
            Toast.makeText(this, R.string.press, Toast.LENGTH_SHORT).show();
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        video_download_layout.performClick();
    }

}