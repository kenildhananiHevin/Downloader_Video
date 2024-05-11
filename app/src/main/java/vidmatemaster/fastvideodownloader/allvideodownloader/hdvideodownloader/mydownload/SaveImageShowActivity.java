package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.REQUEST_PERM_DELETE;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.convertBytesToMB;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.formatDuration;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.getVideoDuration;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.showToast;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.DownloadedMediaInfoModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorShowActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveImageShowActivity extends BaseActivity implements SaveImageListAdapter.DeleteData {
    ArrayList<File> listFile;
    RecyclerView recyclerSingleVideolist;
    LinearLayout empty_list;
    SaveImageListAdapter saveImageListAdapter;
    ArrayList<DownloadedMediaInfoModel> downloadedMediaInfoModelList = new ArrayList<>();
    int pos = -1;
    SaveImageShowActivity activity;
    TextView frame_header_text;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image_show);

        activity = this;

        listFile = new ArrayList<>();

        recyclerSingleVideolist = findViewById(R.id.recyclerSingleVideolist);
        empty_list = findViewById(R.id.empty_list);
        back = findViewById(R.id.back);
        frame_header_text = findViewById(R.id.frame_header_text);

        frame_header_text.setSelected(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

    private void getAllFiles() throws IOException {
        if (DpCreatorShowActivity.SaveVideoPathDirectory.listFiles() != null) {
            for (File file : DpCreatorShowActivity.SaveVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
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
            GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
            recyclerSingleVideolist.setLayoutManager(layoutManager);
            saveImageListAdapter = new SaveImageListAdapter(activity, downloadedMediaInfoModelList, activity, activity);
            recyclerSingleVideolist.setAdapter(saveImageListAdapter);
        } else {
            empty_list.setVisibility(View.VISIBLE);
            downloadedMediaInfoModelList.clear();
            saveImageListAdapter = new SaveImageListAdapter(activity, downloadedMediaInfoModelList, activity, activity);
            recyclerSingleVideolist.setAdapter(saveImageListAdapter);
        }
    }

    @Override
    public void deleteclick(File str, int i) {
        pos = i;
        if (Build.VERSION.SDK_INT >= 30) {
            Intent b = new Intent();
            b.putExtra("pos", i);
            b.putExtra("flag", true);
            List<File> list = new ArrayList<>();
            list.add(str);
            CommonClass.deleteFiles(list, REQUEST_PERM_DELETE, activity, b);
        } else {
            File file = str;
            if (file.exists()) {
                if (file.delete()) {
                    removeAt(pos);
                    showToast(activity, getString(R.string.fileDeletedSuccessfully));
                    return;
                }
                showToast(activity, getString(R.string.fileNotDeleted));
            }
        }
    }

    private void removeAt(int pos) {
        downloadedMediaInfoModelList.remove(pos);
        saveImageListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERM_DELETE && resultCode == -1) {
            removeAt(pos);
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}