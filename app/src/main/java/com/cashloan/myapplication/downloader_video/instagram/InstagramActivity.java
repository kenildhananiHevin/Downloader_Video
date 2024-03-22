package com.cashloan.myapplication.downloader_video.instagram;

import static com.cashloan.myapplication.downloader_video.R.color.language_bg;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.REQUEST_PERM_DELETE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.utils.Utils;
import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.instagram.DownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.instagram.InstagramFragment;
import com.cashloan.myapplication.downloader_video.model.CustomViewPager;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.cashloan.myapplication.downloader_video.other.SharedPre;

import java.util.ArrayList;
import java.util.List;

public class InstagramActivity extends BaseActivity {
    CustomViewPager viewPager;
    CustomViewPagerAdapter adapter;
    InstagramActivity activity;
    InstagramFragment instagramFragment = new InstagramFragment();
    DownloadFragment downloadFragment = new DownloadFragment();
    ImageView backl,instagram_login;
    TextView instagram_download, instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

        activity = this;

        viewPager = findViewById(R.id.view_pager);
        instagram_download = findViewById(R.id.instagram_download);
        instagram = findViewById(R.id.instagram);
        backl = findViewById(R.id.back);
        instagram_login = findViewById(R.id.instagram_login);

        backl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        instagram_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog delete_dialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.d_dialog, null);
                delete_dialog.setView(view1);
                delete_dialog.setCanceledOnTouchOutside(false);
                TextView yes = view1.findViewById(R.id.yes);
                TextView cancel = view1.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.OpenApp(activity, "com.instagram.android");
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

        adapter = new CustomViewPagerAdapter(activity, getSupportFragmentManager());
        adapter.addFragment(instagramFragment);
        adapter.addFragment(downloadFragment);
        instagram.setBackground(getDrawable(R.drawable.how_to_text_bg));
        instagram.setTextColor(getColor(R.color.white));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    instagram.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    instagram.setTextColor(getColor(R.color.white));
                    instagram_download.setBackground(getDrawable(R.drawable.save_bg));
                    instagram_download.setTextColor(getColor(R.color.black));
                } else if (position == 1) {
                    instagram_download.setBackground(getDrawable(R.drawable.how_to_text_bg));
                    instagram_download.setTextColor(getColor(R.color.white));
                    instagram.setBackground(getDrawable(R.drawable.save_bg));
                    instagram.setTextColor(getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        instagram_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }


    private class CustomViewPagerAdapter extends FragmentPagerAdapter {
        InstagramActivity activity;
        List<Fragment> list = new ArrayList<>();

        public CustomViewPagerAdapter(InstagramActivity activity, @NonNull FragmentManager fm) {
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