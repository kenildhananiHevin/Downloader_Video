package com.cashloan.myapplication.downloader_video.facebook;

import static com.cashloan.myapplication.downloader_video.other.CommonClass.REQUEST_PERM_DELETE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.facebook.FaceBookDownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.facebook.FaceBookFragment;
import com.cashloan.myapplication.downloader_video.model.CustomViewPager;
import com.cashloan.myapplication.downloader_video.other.CommonClass;

import java.util.ArrayList;
import java.util.List;

public class FaceBookActivity extends BaseActivity {
    CustomViewPager viewPager;
    CustomAdapter adapter;
    FaceBookActivity activity;
    FaceBookFragment faceBookFragment = new FaceBookFragment();
    FaceBookDownloadFragment faceBookDownloadFragment = new FaceBookDownloadFragment();
    ImageView back,fb_login;
    TextView facebook, facebook_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book);

        activity = this;

        viewPager = findViewById(R.id.view_pager);
        facebook = findViewById(R.id.facebook);
        facebook_download = findViewById(R.id.facebook_download);
        back = findViewById(R.id.back);
        fb_login = findViewById(R.id.fb_login);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog delete_dialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.d_dialog, null);
                delete_dialog.setView(view1);
                delete_dialog.setCanceledOnTouchOutside(false);
                TextView yes = view1.findViewById(R.id.yes);
                TextView cancel = view1.findViewById(R.id.cancel);
                TextView text = view1.findViewById(R.id.text);

                text.setText(R.string.open_facebook);



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.OpenApp(activity, "com.facebook.katana");
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

        adapter = new CustomAdapter(activity, getSupportFragmentManager());
        adapter.addFragment(faceBookFragment);
        adapter.addFragment(faceBookDownloadFragment);
        facebook.setBackground(getDrawable(R.drawable.how_to_text_bg));
        facebook.setTextColor(getColor(R.color.white));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    facebook.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    facebook.setTextColor(getColor(R.color.white));
                    facebook_download.setBackground(getDrawable(R.drawable.save_bg));
                    facebook_download.setTextColor(getColor(R.color.black));
                } else if (position == 1) {
                    facebook_download.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    facebook_download.setTextColor(getColor(R.color.white));
                    facebook.setBackground(getDrawable(R.drawable.save_bg));
                    facebook.setTextColor(getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        facebook_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

    }

    private class CustomAdapter extends FragmentPagerAdapter {
        FaceBookActivity activity;
        List<Fragment> list = new ArrayList<>();

        public CustomAdapter(FaceBookActivity activity, @NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        public void addFragment(Fragment fragment) {
            list.add(fragment);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}