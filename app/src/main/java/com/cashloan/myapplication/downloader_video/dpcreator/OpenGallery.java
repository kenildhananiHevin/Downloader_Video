package com.cashloan.myapplication.downloader_video.dpcreator;

import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.AlbumAdapter;
import com.cashloan.myapplication.downloader_video.adapter.ImageShowAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpenGallery extends BaseActivity {

    ImageView back;
    TextView select_text;
    RecyclerView select_recycle,image_recycle;
    OpenGallery activity;
    AlbumAdapter albumAdapter;
    ImageShowAdapter imageShowAdapter;

    List<Pair<String, List<String>>> folderlist = new ArrayList<>();
    private MutableLiveData<String> latestImagePathLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gallery);

        activity = this;

        back = findViewById(R.id.back);
        select_text = findViewById(R.id.select_text);
        select_recycle = findViewById(R.id.select_recycle);
        image_recycle = findViewById(R.id.image_recycle);

        select_text.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        folderlist = AlbumLoadImages(activity);
        updateImageViewWithLatestImage();

        ContentObserver contentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                updateImagePaths();
            }
        };
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getContentResolver().registerContentObserver(uri, true, contentObserver);


    }

    private void updateImagePaths() {
        folderlist = AlbumLoadImages(activity);
        if (select_recycle.getAdapter() != null) {
            select_recycle.getAdapter().notifyDataSetChanged();
        }
    }

    private void updateImageViewWithLatestImage() {
        if (!folderlist.isEmpty()) {
            String latestImagePath = folderlist.get(0).second.get(0);
            latestImagePathLiveData.setValue(latestImagePath);
        }
    }

    private List<Pair<String, List<String>>> AlbumLoadImages(OpenGallery activity) {
        Uri uri;
        Cursor cursor;
        String absolutePathOfImage;

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        cursor = activity.getContentResolver().query(uri, projection, null, null, sortOrder);

        List<Pair<String, List<String>>> folderlist = new ArrayList<>();
        List<String> allImages = new ArrayList<>();
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            if (new File(absolutePathOfImage).exists()) {
                String foldername = new File(Objects.requireNonNull(new File(absolutePathOfImage).getParent())).getName();
                if (foldername.equals("0")) {
                    continue;
                }
                Pair<String, List<String>> folderPair = null;
                for (Pair<String, List<String>> pair : folderlist) {
                    if (pair.first.equals(foldername)) {
                        folderPair = pair;
                        Log.d("Camera", foldername);
                        break;
                    }
                }
                if (folderPair == null) {
                    folderPair = new Pair<>(foldername, new ArrayList<>());
                    folderlist.add(folderPair);
                }
                folderPair.second.add(absolutePathOfImage);
                allImages.add(absolutePathOfImage);
            }
        }

        Pair<String, List<String>> allImagesFolder = new Pair<>("All Images", new ArrayList<>());
        cursor.moveToFirst();
        do {
            absolutePathOfImage = cursor.getString(column_index_data);
            allImagesFolder.second.add(absolutePathOfImage);
        } while (cursor.moveToNext());

        folderlist.add(0, allImagesFolder);
        select_recycle.setLayoutManager(new LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false));
        albumAdapter = new AlbumAdapter(activity, folderlist, new AlbumAdapter.Click() {
            @Override
            public void onClick(List<String> list) {
                image_recycle.setLayoutManager(new GridLayoutManager(activity,3));
                imageShowAdapter = new ImageShowAdapter(activity,list);
                image_recycle.setAdapter(imageShowAdapter);
            }
        });
        select_recycle.setAdapter(albumAdapter);

        return folderlist;

    }
}