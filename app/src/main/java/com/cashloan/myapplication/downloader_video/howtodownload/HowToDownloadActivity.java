package com.cashloan.myapplication.downloader_video.howtodownload;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.howtodownload.FaceBookHowToDownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.howtodownload.InstagramHowToDownloadFragment;
import com.cashloan.myapplication.downloader_video.fragment.howtodownload.TwitterHowToDownloadFragment;
import com.cashloan.myapplication.downloader_video.model.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HowToDownloadActivity extends BaseActivity {

    TextView how_to_header;
    ImageView back;
    TabLayout tabLayout;
    CustomViewPager viewPager;
    HowToDownloadActivity activity;
    InstagramHowToDownloadFragment instagramHowToDownloadFragment = new InstagramHowToDownloadFragment();
    FaceBookHowToDownloadFragment faceBookDownloadFragment = new FaceBookHowToDownloadFragment();
    TwitterHowToDownloadFragment twitterDownloadFragment = new TwitterHowToDownloadFragment();
    HowToDownloadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_download);

        activity = this;

        back = findViewById(R.id.back);
        how_to_header = findViewById(R.id.how_to_header);
        tabLayout = findViewById(R.id.tab_layouts);
        viewPager = findViewById(R.id.view_pager);

        how_to_header.setSelected(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(R.string.instagram));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.facebook));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.twitter));

        adapter = new HowToDownloadAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(instagramHowToDownloadFragment);
        adapter.addFragment(faceBookDownloadFragment);
        adapter.addFragment(twitterDownloadFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
    }

    private class HowToDownloadAdapter extends FragmentPagerAdapter {
        List<Fragment> list = new ArrayList<>();
        int tabCount;

        public HowToDownloadAdapter(HowToDownloadActivity activity, @NonNull FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        public void addFragment(Fragment fragment) {
            list.add(fragment);
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}