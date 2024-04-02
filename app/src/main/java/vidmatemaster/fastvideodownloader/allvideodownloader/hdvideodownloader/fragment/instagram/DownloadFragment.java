package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.instagram;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.convertBytesToMB;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.formatDuration;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.getVideoDuration;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.mEnstagramVideoPathDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.DownloadedVideoListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.DownloadedMediaInfoModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadFragment extends Fragment{
    ArrayList<File> listFile;
    RecyclerView recyclerSingleVideolist;
    LinearLayout empty_list;
    DownloadedVideoListAdapter downloadedVideoListAdapter;
    ArrayList<DownloadedMediaInfoModel> downloadedMediaInfoModelList = new ArrayList<>();
    int pos = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        listFile = new ArrayList<>();

        recyclerSingleVideolist = view.findViewById(R.id.recyclerSingleVideolist);
        empty_list = view.findViewById(R.id.empty_list);

        mutableLiveData.observe(requireActivity(), new Observer<String>() {
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

    public static MutableLiveData<String> mutableLiveData = new MutableLiveData<String>();

    private void getAllFiles() throws IOException {
        if (mEnstagramVideoPathDirectory.listFiles() != null) {
            for (File file : mEnstagramVideoPathDirectory.listFiles()) {
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
            downloadedVideoListAdapter = new DownloadedVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(downloadedVideoListAdapter);
        } else {
            empty_list.setVisibility(View.VISIBLE);
            downloadedMediaInfoModelList.clear();
            downloadedVideoListAdapter = new DownloadedVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(downloadedVideoListAdapter);
        }
    }
}