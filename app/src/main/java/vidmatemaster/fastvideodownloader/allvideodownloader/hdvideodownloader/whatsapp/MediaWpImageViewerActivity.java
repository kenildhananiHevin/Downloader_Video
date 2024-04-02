package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.REQUEST_PERM_DELETE;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.showToast;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.ConstMedia;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload.SaveImageListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaWpImageViewerActivity extends BaseActivity implements SaveImageListAdapter.DeleteData {
    public String SaveFilePath = (ConstMedia.RootDirectoryWhatsappShow + "/");
    ImageView imageView, share, delete;
    String image_path = "";
    String package_name = "";
    String path = "";
    String type = "";
    TextView txtTitle;
    SaveImageListAdapter.DeleteData deleteData;
    MediaWpImageViewerActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity_whatz_image_viewer);

        activity = this;
        deleteData = this;

        String title = getIntent().getStringExtra("name");

        txtTitle = findViewById(R.id.txtTitle);
        share = findViewById(R.id.share);
        delete = findViewById(R.id.delete);
        imageView = findViewById(R.id.imageView);

        txtTitle.setSelected(true);
        txtTitle.setText(title);

        Intent intent = getIntent();
        if (intent != null) {
            image_path = intent.getStringExtra("image");
            type = intent.getStringExtra("type");
            package_name = intent.getStringExtra("pack");
            if (image_path != null) {
                Glide.with(activity).load(image_path).into(imageView);
            }
        }

        String wp_s = getIntent().getStringExtra("from");

        if (wp_s != null && wp_s.equals("whatsapp")) {
            share.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass.shareImage(activity, image_path);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog delete_dialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.dialog_delete, null);
                delete_dialog.setView(view1);
                delete_dialog.setCanceledOnTouchOutside(false);
                TextView cancel = view1.findViewById(R.id.delete_cancel);
                TextView delete_btn = view1.findViewById(R.id.delete_ok);
                TextView delete = view1.findViewById(R.id.delete);
                TextView delete_txt = view1.findViewById(R.id.delete_txt);

                delete.setText(R.string.delete_image);
                delete_txt.setText(R.string.are_you_sure_you_want_to_delete_this_image);


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData.deleteclick(new File(image_path), 0);
                        delete_dialog.dismiss();
                    }
                });

                delete_dialog.show();
                Window window = delete_dialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.80);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });

        (findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void deleteclick(File str, int i) {
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
                    showToast(activity, getString(R.string.fileDeletedSuccessfully));
                    finish();
                    return;
                }
                showToast(activity, getString(R.string.fileNotDeleted));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERM_DELETE && resultCode == -1) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}