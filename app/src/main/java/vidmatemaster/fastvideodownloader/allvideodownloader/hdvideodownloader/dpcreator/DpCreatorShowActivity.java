package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.EditPicAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.dpimage.MultiTouchListener;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.creator_model.DpCreator;
import com.google.android.material.slider.Slider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DpCreatorShowActivity extends BaseActivity {

    ImageView back, select_pic;
    public static ImageView frame_select;
    TextView photo_click, frame_header_text,save;
    DpCreatorShowActivity activity;
    RecyclerView recycle_edit;
    EditPicAdapter editPicAdapter;
    ArrayList<DpCreator> list = new ArrayList<>();
    private Slider zoomSeekBar;
    private float scaleFactor = 1f;
    private float maxScaleFactor = 3f;
    FrameLayout frame_layout;
    public static File SaveVideoPathDirectory = new File(Environment.getExternalStorageDirectory() + "/Download/Video_Downloader/SavedPhotos");
    String images;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_creator_show);

        activity = this;

        list.add(new DpCreator(R.drawable.frame_7));
        list.add(new DpCreator(R.drawable.frame_8));
        list.add(new DpCreator(R.drawable.frame_9));
        list.add(new DpCreator(R.drawable.frame_10));
        list.add(new DpCreator(R.drawable.frame_11));
        list.add(new DpCreator(R.drawable.frame_12));
        list.add(new DpCreator(R.drawable.frame_39));
        list.add(new DpCreator(R.drawable.frame_40));
        list.add(new DpCreator(R.drawable.frame_41));
        list.add(new DpCreator(R.drawable.frame_42));
        list.add(new DpCreator(R.drawable.frame_43));
        list.add(new DpCreator(R.drawable.frame_44));
        list.add(new DpCreator(R.drawable.frame_45));
        list.add(new DpCreator(R.drawable.frame_46));
        list.add(new DpCreator(R.drawable.frame_47));
        list.add(new DpCreator(R.drawable.frame_48));
        list.add(new DpCreator(R.drawable.frame_49));
        list.add(new DpCreator(R.drawable.frame_50));
        list.add(new DpCreator(R.drawable.frame_51));
        list.add(new DpCreator(R.drawable.frame_52));
        list.add(new DpCreator(R.drawable.frame_53));
        list.add(new DpCreator(R.drawable.frame_54));
        list.add(new DpCreator(R.drawable.frame_55));
        list.add(new DpCreator(R.drawable.frame_56));
        list.add(new DpCreator(R.drawable.frame_57));
        list.add(new DpCreator(R.drawable.frame_58));
        list.add(new DpCreator(R.drawable.frame_59));
        list.add(new DpCreator(R.drawable.frame_60));
        list.add(new DpCreator(R.drawable.frame_61));
        list.add(new DpCreator(R.drawable.frame_62));
        list.add(new DpCreator(R.drawable.frame_63));
        list.add(new DpCreator(R.drawable.frame_64));

        images = getIntent().getStringExtra("image");

        int imagess = getIntent().getIntExtra("images", 0);

        back = findViewById(R.id.back);
        select_pic = findViewById(R.id.select_pic);
        frame_select = findViewById(R.id.frame_select);
        photo_click = findViewById(R.id.photo_click);
        frame_header_text = findViewById(R.id.frame_header_text);
        zoomSeekBar = findViewById(R.id.seekBar);
        recycle_edit = findViewById(R.id.recycle_edit);
        save = findViewById(R.id.save);
        frame_layout = findViewById(R.id.frame_layout);

        frame_header_text.setSelected(true);

        Glide.with(activity).load(images).into(select_pic);

//        Glide.with(activity).load(imagess).into(frame_select);
        frame_select.setImageResource(imagess);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        photo_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, OpenGallery.class);
                startActivityForResult(i, 100);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images != null){
                    frame_layout.invalidate();
                    frame_layout.setDrawingCacheEnabled(true);
                    frame_layout.buildDrawingCache();
                    Bitmap bitmap = frame_layout.getDrawingCache();
                    Toast.makeText(activity, getString(R.string.save), Toast.LENGTH_SHORT).show();
                    if (bitmap != null) {
                        saveFile(bitmap);
                    } else {
                        Toast.makeText(activity, "Failed to obtain the bitmap", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(activity, getString(R.string.selected_image), Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*zoomSeekBar.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                scaleFactor = 1f + (maxScaleFactor - 1f) * value / 100f;
                updateZoom();
            }
        });*/

        select_pic.setOnTouchListener(new MultiTouchListener());

      /*  select_pic.setOnTouchListener(new View.OnTouchListener() {
        *//*    private float lastX, lastY;
            private float dX, dY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        lastX = v.getX();
                        lastY = v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + dX;
                        float newY = event.getRawY() + dY;
                        v.animate().x(newX).y(newY).setDuration(0).start();
                        lastX = newX;
                        lastY = newY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (lastX < 0 || lastY < 0 || lastX + v.getWidth() > select_pic.getWidth() || lastY + v.getHeight() > select_pic.getHeight()) {
                            float finalX = Math.max(0, Math.min(lastX, select_pic.getWidth() - v.getWidth()));
                            float finalY = Math.max(0, Math.min(lastY, select_pic.getHeight() - v.getHeight()));
                            v.animate().x(finalX).y(finalY).start();
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }*//*
        });*/

        recycle_edit.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        editPicAdapter = new EditPicAdapter(activity, list);
        recycle_edit.setAdapter(editPicAdapter);
    }

    private Uri saveFile(Bitmap bitmap) {
        if (!SaveVideoPathDirectory.exists()) {
            SaveVideoPathDirectory.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        File imageFile = new File(SaveVideoPathDirectory, imageFileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        scanFile(activity, imageFile.getAbsolutePath());
        return null;
    }

   /* private void updateZoom() {
        try {
            int viewWidth = select_pic.getWidth();
            int viewHeight = select_pic.getHeight();
            int drawableWidth = select_pic.getDrawable().getIntrinsicWidth();
            int drawableHeight = select_pic.getDrawable().getIntrinsicHeight();

            float scaleX = (float) viewWidth / (float) drawableWidth;
            float scaleY = (float) viewHeight / (float) drawableHeight;

            float scale = Math.min(scaleX, scaleY) * scaleFactor;

            float translateX = (viewWidth - (drawableWidth * scale)) / 2f;
            float translateY = (viewHeight - (drawableHeight * scale)) / 2f;

            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate(translateX, translateY);
            select_pic.setImageMatrix(matrix);
            select_pic.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void onBackPressed() {
        backPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == requestCode && resultCode == RESULT_OK) {
            images = data.getStringExtra("image");
            Glide.with(activity).load(images).into(select_pic);
        }
    }
}