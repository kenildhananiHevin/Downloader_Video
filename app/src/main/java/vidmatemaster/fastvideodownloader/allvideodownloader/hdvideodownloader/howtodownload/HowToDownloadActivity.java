package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.howtodownload;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.howtodownload.FaceBookHowToDownloadFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.howtodownload.InstagramHowToDownloadFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.howtodownload.WhatsappHowToDownloadFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.CustomViewPager;
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
    WhatsappHowToDownloadFragment whatsappHowToDownloadFragment = new WhatsappHowToDownloadFragment();
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
                onBackPressed();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(R.string.instagram));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.facebook));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.whatsapp));

        adapter = new HowToDownloadAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        adapter.addFragment(instagramHowToDownloadFragment);
        adapter.addFragment(faceBookDownloadFragment);
        adapter.addFragment(whatsappHowToDownloadFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    viewPager.setCurrentItem(0);
                } else if (tab.getPosition() == 1) {
                    viewPager.setCurrentItem(1);
                } else if (tab.getPosition() == 2) {
                    viewPager.setCurrentItem(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(2);

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

    @Override
    public void onBackPressed() {
        backPressed();
    }
}