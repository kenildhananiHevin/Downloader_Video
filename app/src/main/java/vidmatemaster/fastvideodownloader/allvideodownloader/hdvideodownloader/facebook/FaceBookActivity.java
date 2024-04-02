package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.facebook.FaceBookDownloadFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.facebook.FaceBookFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.CustomViewPager;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;

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
                onBackPressed();
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
                hideKeyboard(activity);
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
                hideKeyboard(activity);
                viewPager.setCurrentItem(1);
            }
        });
    }

    private void hideKeyboard(FaceBookActivity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
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

    @Override
    public void onBackPressed() {
        backPressed();
    }
}