package com.cashloan.myapplication.downloader_video.whatsapp;

import static com.cashloan.myapplication.downloader_video.other.CommonClass.REQUEST_PERM_DELETE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.view.PointerIconCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.whatsapp.MediaDownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.whatsapp.MediaImageFragment;
import com.cashloan.myapplication.downloader_video.model.CustomViewPager;
import com.cashloan.myapplication.downloader_video.utils.iUtilsMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaWpStatusActivity extends BaseActivity {
    CustomViewPager viewPager;
    RelativeLayout mainLayout;
    FrameLayout flContainer;
    AppCompatButton btn_grant;
    ImageView back, permission_image;
    TextView btn_cancel, btn_ok, whatsapp_header, whatsapp, whatsapp_download, permission_text, permission_text_2;
    MediaWpStatusActivity activity;
    Intent intent_type;
    String status_type;
    public static String namedataprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_wp_status);
        try {
            intent_type = getIntent();
            status_type = intent_type.getStringExtra("status_type");

            activity = this;
            mainLayout = findViewById(R.id.mainLayout);
            btn_grant = findViewById(R.id.btn_grant);
            flContainer = findViewById(R.id.flContainer);
            back = findViewById(R.id.back);
            whatsapp_header = findViewById(R.id.whatsapp_header);
            whatsapp = findViewById(R.id.whatsapp);
            whatsapp_download = findViewById(R.id.whatsapp_download);
            permission_image = findViewById(R.id.permission_image);
            permission_text = findViewById(R.id.permission_text);
            permission_text_2 = findViewById(R.id.permission_text_2);
            whatsapp_header.setSelected(true);

            viewPager = findViewById(R.id.viewpager);

            namedataprefs = getSharedPreferences("whatsapp_pref", 0).getString("whatsapp", "");


            btn_grant.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(MediaWpStatusActivity.this, R.style.MyTransparentBottomSheetDialogTheme).create();
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View view1 = layoutInflater.inflate(R.layout.media_whatsaap_per_dialog, null);
                    dialog.setView(view1);
                    dialog.setCanceledOnTouchOutside(false);

                    btn_ok = view1.findViewById(R.id.btn_ok);
                    btn_cancel = view1.findViewById(R.id.btn_cancel);

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            grantAnd11permission();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    Window window = dialog.getWindow();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int screenWidth = displayMetrics.widthPixels;
                    int dialogWidth = (int) (screenWidth * 0.80);
                    window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);
                }
            });
            if (namedataprefs.equals("")) {
                btn_grant.setVisibility(View.VISIBLE);
                permission_image.setVisibility(View.VISIBLE);
                permission_text.setVisibility(View.VISIBLE);
                permission_text_2.setVisibility(View.VISIBLE);
            } else if (getFromSdcard() != null) {
                btn_grant.setVisibility(View.GONE);
                permission_image.setVisibility(View.GONE);
                permission_text.setVisibility(View.GONE);
                permission_text_2.setVisibility(View.GONE);
                viewPager.setOffscreenPageLimit(0);
                setupViewPager(this.viewPager);
            } else {
                btn_grant.setVisibility(View.VISIBLE);
                permission_image.setVisibility(View.VISIBLE);
                permission_text.setVisibility(View.VISIBLE);
                permission_text_2.setVisibility(View.VISIBLE);
            }

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
        }
    }

    MediaDownloadFragment mediaDownloadFragment;
    MediaImageFragment mediaImageFragment;

    private void setupViewPager(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mediaImageFragment = new MediaImageFragment();
        viewPagerAdapter.addFragment(mediaImageFragment, getString(R.string.image));

        mediaDownloadFragment = new MediaDownloadFragment();
        viewPagerAdapter.addFragment(mediaDownloadFragment, getString(R.string.videoss));

        whatsapp.setBackground(getDrawable(R.drawable.how_to_text_bg));
        whatsapp.setTextColor(getColor(R.color.white));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    whatsapp.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    whatsapp.setTextColor(getColor(R.color.white));
                    whatsapp_download.setBackground(getDrawable(R.drawable.save_bg));
                    whatsapp_download.setTextColor(getColor(R.color.black));
                } else if (position == 1) {
                    whatsapp_download.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    whatsapp_download.setTextColor(getColor(R.color.white));
                    whatsapp.setBackground(getDrawable(R.drawable.save_bg));
                    whatsapp.setTextColor(getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setCurrentItem(0);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        whatsapp_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });


    }

    private DocumentFile[] getFromSdcard() {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(this, Uri.parse(this.namedataprefs));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return this.mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String str) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(str);
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return this.mFragmentTitleList.get(i);
        }
    }

    @SuppressLint("WrongConstant")
    public void grantAnd11permission() {
        Intent intent;
        iUtilsMedia.isPackageInstalled(this, "com.whatsapp");
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        String whatsupFolder = getWhatsupFolder();
        if (Build.VERSION.SDK_INT >= 29) {
            intent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
            String replace = intent.getParcelableExtra("android.provider.extra.INITIAL_URI").toString().replace("/root/", "/document/");
            intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + whatsupFolder));
        } else {
            intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(whatsupFolder));
        }
        intent.addFlags(2);
        intent.addFlags(1);
        intent.addFlags(128);
        intent.addFlags(64);
        startActivityForResult(intent, PointerIconCompat.TYPE_COPY);
    }

    public String getWhatsupFolder() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("Android/media/com.whatsapp/WhatsApp");
        sb.append(File.separator);
        sb.append("Media");
        sb.append(File.separator);
        sb.append(".Statuses");
        return new File(sb.toString()).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses" : "WhatsApp%2FMedia%2F.Statuses";
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 123) {
            btn_grant.setVisibility(View.GONE);
            permission_image.setVisibility(View.GONE);
            permission_text.setVisibility(View.GONE);
            permission_text_2.setVisibility(View.GONE);
            viewPager.setOffscreenPageLimit(0);
            setupViewPager(this.viewPager);
        } else if (i == 1011 && i2 == -1) {
            Uri data = intent.getData();
            try {
                getContentResolver().takePersistableUriPermission(data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception e) {
                e.printStackTrace();
            }
           namedataprefs = data.toString();
            SharedPreferences.Editor edit = getSharedPreferences("whatsapp_pref", 0).edit();
            edit.putString("whatsapp", data.toString());
            edit.apply();
            if (namedataprefs.equals("")) {
                btn_grant.setVisibility(View.VISIBLE);
                permission_image.setVisibility(View.VISIBLE);
                permission_text.setVisibility(View.VISIBLE);
                permission_text_2.setVisibility(View.VISIBLE);
            } else if (getFromSdcard() != null) {
                btn_grant.setVisibility(View.GONE);
                permission_image.setVisibility(View.GONE);
                permission_text.setVisibility(View.GONE);
                permission_text_2.setVisibility(View.GONE);
                viewPager.setOffscreenPageLimit(0);
                setupViewPager(this.viewPager);
            } else {
                btn_grant.setVisibility(View.VISIBLE);
                permission_image.setVisibility(View.VISIBLE);
                permission_text.setVisibility(View.VISIBLE);
                permission_text_2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length == 0 || iArr[0] != 0) {
                checkAgain();
            } else {

            }
        }
    }

    public void checkAgain() {
        if (Build.VERSION.SDK_INT > 32) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, String.valueOf(new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(R.string.pernecessory);
                builder.setMessage(R.string.write_neesory);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        lambda$checkAgain$0$WhatzRecentStatusActivity(dialogInterface, i);
                    }
                });
                builder.create().show();
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 123);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle(R.string.pernecessory);
                builder.setMessage(R.string.write_neesory);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        lambda$checkAgain$0$WhatzRecentStatusActivity(dialogInterface, i);
                    }
                });
                builder.create().show();
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 123);
        }

    }

    public void lambda$checkAgain$0$WhatzRecentStatusActivity(DialogInterface dialogInterface, int i) {
        if (Build.VERSION.SDK_INT > 32) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 123);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 123);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}