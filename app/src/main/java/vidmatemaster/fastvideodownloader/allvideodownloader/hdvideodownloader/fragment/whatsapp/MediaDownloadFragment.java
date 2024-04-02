package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.whatsapp;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.ConstMedia.RootDirectoryWhatsappShow;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.convertBytesToMB;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.formatDuration;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.getVideoDuration;

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

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.DownloadedWhatsappVideoListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.DownloadedMediaInfoModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
