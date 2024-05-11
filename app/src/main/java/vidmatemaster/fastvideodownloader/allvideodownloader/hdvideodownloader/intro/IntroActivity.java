package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity.MainActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.introf.CopyFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.introf.PasteFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.introf.SearchFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.CustomViewPager;

public class IntroActivity extends BaseActivity {

    CustomViewPager viewPager;
    IntroActivity activity;
    IntroAdapter adapter;
    TextView txtNext, txtCopy,txtSkipIntro;
    ImageView imgIcon,imgChange;


    SearchFragment searchFragment = new SearchFragment();
    CopyFragment copyFragment = new CopyFragment();
    PasteFragment pasteFragment = new PasteFragment();

    boolean backClick = false;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setNavigationBarColor(getColor(R.color.white));
        getWindow().setStatusBarColor(getColor(R.color.white));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        nativeAdMedium();

        activity = this;

        viewPager = findViewById(R.id.view_pager);
        txtNext = findViewById(R.id.txtNext);
        txtCopy = findViewById(R.id.txtCopy);
        imgIcon = findViewById(R.id.imgIcon);
        imgChange = findViewById(R.id.imgChange);
        txtSkipIntro = findViewById(R.id.txtSkipIntro);

        txtCopy.setSelected(true);

        adapter = new IntroAdapter(activity, getSupportFragmentManager(), 3);
        adapter.addFragment(searchFragment);
        adapter.addFragment(copyFragment);
        adapter.addFragment(pasteFragment);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                activity.position = position;
                if (position == 0) {
                    imgIcon.setImageResource(R.drawable.one_search);
                    imgChange.setImageResource(R.drawable.one);
                    txtCopy.setText(getString(R.string.search_video));
                    txtNext.setText(getString(R.string.next));
                    txtSkipIntro.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    imgIcon.setImageResource(R.drawable.two_copy);
                    imgChange.setImageResource(R.drawable.two);
                    txtCopy.setText(getString(R.string.copy_link));
                    txtNext.setText(getString(R.string.next));
                    txtSkipIntro.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    imgIcon.setImageResource(R.drawable.three_paste);
                    imgChange.setImageResource(R.drawable.three);
                    txtCopy.setText(getString(R.string.paste_link_and_download));
                    txtNext.setText(getString(R.string.got_its));
                    txtSkipIntro.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                viewPager.setCurrentItem(position);
                if (position == 0) {
                } else if (position == 1) {

                } else if (position == 2) {

                }else {
                    startActivity(new Intent(activity, MainActivity.class));
                }
            }
        });

        txtSkipIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MainActivity.class));
            }
        });


    }

    private class IntroAdapter extends FragmentPagerAdapter {
        List<Fragment> list = new ArrayList<>();
        int tabCount;

        public IntroAdapter(IntroActivity activity, @NonNull FragmentManager fm, int tabCount) {
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
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                backClick = false;
            }
        }, 2000);
        if (backClick) {
            finishAffinity();
        } else {
            backClick = true;
            Toast.makeText(activity, R.string.press, Toast.LENGTH_SHORT).show();
        }
    }
}