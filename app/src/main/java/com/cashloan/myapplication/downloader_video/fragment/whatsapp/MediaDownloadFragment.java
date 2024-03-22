package com.cashloan.myapplication.downloader_video.fragment.whatsapp;

import static com.cashloan.myapplication.downloader_video.model.ConstMedia.RootDirectoryWhatsappShow;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.REQUEST_PERM_DELETE;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.convertBytesToMB;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.formatDuration;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.getVideoDuration;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.mEnstagramVideoPathDirectory;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.showToast;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.DownloadedVideoListAdapter;
import com.cashloan.myapplication.downloader_video.adapter.DownloadedWhatsappVideoListAdapter;
import com.cashloan.myapplication.downloader_video.model.ConstMedia;
import com.cashloan.myapplication.downloader_video.model.DownloadedMediaInfoModel;
import com.cashloan.myapplication.downloader_video.model.insta_model.StoryModelMedia;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.cashloan.myapplication.downloader_video.utils.MediaFilePathUtility;
import com.cashloan.myapplication.downloader_video.utils.iUtilsMedia;
import com.cashloan.myapplication.downloader_video.whatsapp.MediaWpStatusActivity;
import com.cashloan.myapplication.downloader_video.whatsapp.MediaWpVIdeoViewerActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;

public class MediaDownloadFragment extends Fragment{
    ArrayList<File> listFile;
    RecyclerView recyclerSingleVideolist;
    LinearLayout empty_list;
    DownloadedWhatsappVideoListAdapter downloadedWhatsappVideoListAdapter;
    ArrayList<DownloadedMediaInfoModel> downloadedMediaInfoModelList = new ArrayList<>();
    int pos = -1;

    public MediaDownloadFragment() {}

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.media_fragment_download, viewGroup, false);

        listFile = new ArrayList<>();

        recyclerSingleVideolist = view.findViewById(R.id.recyclerSingleVideolist);
        empty_list = view.findViewById(R.id.empty_list);

        mutableLiveDatas.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    listFile.clear();
                    getAllFiles();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            listFile.clear();
            getAllFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MutableLiveData<String> mutableLiveDatas = new MutableLiveData<String>();

    private void getAllFiles() throws IOException {
        if (RootDirectoryWhatsappShow.listFiles() != null) {
            for (File file : RootDirectoryWhatsappShow.listFiles()) {
                if (!file.getName().startsWith(".")){
                    listFile.add(file);
                }
            }
        } else {
            empty_list.setVisibility(View.VISIBLE);
        }

        if (!listFile.isEmpty()) {
            downloadedMediaInfoModelList.clear();
            for (File file : listFile) {
                if (file.isFile() && file.getName().endsWith(".mp4")) {
                    DownloadedMediaInfoModel downloadedMediaInfoModel = new DownloadedMediaInfoModel();
                    downloadedMediaInfoModel.setMediaFile(file);
                    downloadedMediaInfoModel.setMediaName(file.getName());
                    downloadedMediaInfoModel.setMediaSize(convertBytesToMB(file.length()));
                    downloadedMediaInfoModel.setMediaDuration(formatDuration(getVideoDuration(file.getAbsolutePath())));
                    downloadedMediaInfoModel.setMediaPath(file.getAbsolutePath());
                    downloadedMediaInfoModelList.add(downloadedMediaInfoModel);
                } else if (file.isFile() && file.getName().endsWith(".jpg")) {
                    DownloadedMediaInfoModel downloadedMediaInfoModel = new DownloadedMediaInfoModel();
                    downloadedMediaInfoModel.setMediaFile(file);
                    downloadedMediaInfoModel.setMediaName(file.getName());
                    downloadedMediaInfoModel.setMediaSize(convertBytesToMB(file.length()));
                    downloadedMediaInfoModel.setMediaPath(file.getAbsolutePath());
                    downloadedMediaInfoModelList.add(downloadedMediaInfoModel);
                }
            }
            empty_list.setVisibility(View.GONE);
            GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
            recyclerSingleVideolist.setLayoutManager(layoutManager);
            downloadedWhatsappVideoListAdapter = new DownloadedWhatsappVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(downloadedWhatsappVideoListAdapter);
        } else {
            empty_list.setVisibility(View.VISIBLE);
            downloadedMediaInfoModelList.clear();
            downloadedWhatsappVideoListAdapter = new DownloadedWhatsappVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(downloadedWhatsappVideoListAdapter);
        }
    }
}
