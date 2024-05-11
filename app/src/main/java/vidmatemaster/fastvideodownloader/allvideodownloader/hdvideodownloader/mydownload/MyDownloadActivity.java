package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.ConstMedia.RootDirectoryWhatsappShow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.MyDownloadAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.my_download.MyDownload;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorShowActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other.FaceBookService;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;

import java.io.File;
import java.util.ArrayList;

public class MyDownloadActivity extends BaseActivity {

    TextView creation_header_text;
    MyDownloadActivity activity;
    ImageView back;
    ArrayList<File> listFile, wp_listFile, fb_listFile, dp_listFile;
    RecyclerView recycle_my_download;
    ArrayList<MyDownload> list = new ArrayList<>();
    MyDownloadAdapter myDownloadAdapter;
    LinearLayout empty_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download);

        activity = this;
        listFile = new ArrayList<>();
        wp_listFile = new ArrayList<>();
        fb_listFile = new ArrayList<>();
        dp_listFile = new ArrayList<>();

        creation_header_text = findViewById(R.id.creation_header_text);
        back = findViewById(R.id.back);
        recycle_my_download = findViewById(R.id.recycle_my_download);
        empty_list = findViewById(R.id.empty_list);

        creation_header_text.setSelected(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        listFile.clear();
        wp_listFile.clear();
        fb_listFile.clear();
        dp_listFile.clear();

        if (CommonClass.mEnstagramVideoPathDirectory.listFiles() != null) {
            for (File file : CommonClass.mEnstagramVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    listFile.add(file);
                }
            }
        }

        if (RootDirectoryWhatsappShow.listFiles() != null) {
            for (File file : RootDirectoryWhatsappShow.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    wp_listFile.add(file);
                }
            }
        }

        if (FaceBookService.mFaceBookVideoPathDirectory.listFiles() != null) {
            for (File file : FaceBookService.mFaceBookVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    fb_listFile.add(file);
                }
            }
        }

        if (DpCreatorShowActivity.SaveVideoPathDirectory.listFiles() != null) {
            for (File file : DpCreatorShowActivity.SaveVideoPathDirectory.listFiles()) {
                if (!file.getName().startsWith(".")) {
                    dp_listFile.add(file);
                }
            }
        }

        if (!listFile.isEmpty()) {
            list.add(new MyDownload("InstagramSaveVideo&Photos", listFile));
        }
        if (!wp_listFile.isEmpty()) {
            list.add(new MyDownload("WhatsappSaver", wp_listFile));
        }
        if (!fb_listFile.isEmpty()) {
            list.add(new MyDownload("FaceBookSavedVideo", fb_listFile));
        }
        if (!dp_listFile.isEmpty()) {
            list.add(new MyDownload("SavedPhotos", dp_listFile));
        }

        recycle_my_download.setLayoutManager(new GridLayoutManager(activity, 2));
        myDownloadAdapter = new MyDownloadAdapter(activity, list);
        if (list.isEmpty()) {
            empty_list.setVisibility(View.VISIBLE);
        } else {
            empty_list.setVisibility(View.GONE);
        }
        recycle_my_download.setAdapter(myDownloadAdapter);
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}