package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.facebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.FaceBookDownloadedVideoListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.DownloadedMediaInfoModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other.FaceBookService;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FaceBookDownloadFragment extends Fragment{
    ArrayList<File> listFile;
    RecyclerView recyclerSingleVideolist;
    LinearLayout empty_list;
    FaceBookDownloadedVideoListAdapter faceBookDownloadedVideoListAdapter;
    ArrayList<DownloadedMediaInfoModel> downloadedMediaInfoModelList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_book_download, container, false);

        listFile = new ArrayList<>();

        recyclerSingleVideolist = view.findViewById(R.id.recyclerSingleVideolist);
        empty_list = view.findViewById(R.id.empty_list);

        mutableLiveDataFb.observe(requireActivity(), new Observer<String>() {
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


    public static MutableLiveData<String> mutableLiveDataFb = new MutableLiveData<String>();

    private void getAllFiles() throws IOException {
        if (FaceBookService.mFaceBookVideoPathDirectory.listFiles() != null) {
            for (File file : FaceBookService.mFaceBookVideoPathDirectory.listFiles()) {
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
                    downloadedMediaInfoModel.setMediaSize(CommonClass.convertBytesToMB(file.length()));
                    downloadedMediaInfoModel.setMediaDuration(CommonClass.formatDuration(CommonClass.getVideoDuration(file.getAbsolutePath())));
                    downloadedMediaInfoModel.setMediaPath(file.getAbsolutePath());
                    downloadedMediaInfoModelList.add(downloadedMediaInfoModel);
                } else if (file.isFile() && file.getName().endsWith(".jpg")) {
                    DownloadedMediaInfoModel downloadedMediaInfoModel = new DownloadedMediaInfoModel();
                    downloadedMediaInfoModel.setMediaFile(file);
                    downloadedMediaInfoModel.setMediaName(file.getName());
                    downloadedMediaInfoModel.setMediaSize(CommonClass.convertBytesToMB(file.length()));
                    downloadedMediaInfoModel.setMediaPath(file.getAbsolutePath());
                    downloadedMediaInfoModelList.add(downloadedMediaInfoModel);
                }
            }
            empty_list.setVisibility(View.GONE);
            GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
            recyclerSingleVideolist.setLayoutManager(layoutManager);
            faceBookDownloadedVideoListAdapter = new FaceBookDownloadedVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(faceBookDownloadedVideoListAdapter);
        } else {
            empty_list.setVisibility(View.VISIBLE);
            downloadedMediaInfoModelList.clear();
            faceBookDownloadedVideoListAdapter = new FaceBookDownloadedVideoListAdapter(requireActivity(), downloadedMediaInfoModelList, this);
            recyclerSingleVideolist.setAdapter(faceBookDownloadedVideoListAdapter);
        }
    }
}