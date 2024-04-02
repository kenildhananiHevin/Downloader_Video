package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.story;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.StoriesListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.UserListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.api.CommonClassForAPI;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.other.InstagramLogin;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.interfaces.UserListInterface;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.facebook_model.NodeModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.FullDetailModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.StoryModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.TrayModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre;

public class InstagramStoryActivity extends BaseActivity {
    TextView instagram_story_text, tvViewStories, private_account;
    ImageView back;
    ProgressBar pr_loading_bar;
    RecyclerView RVUserList, RVStories;
    SwitchCompat switchCompat;
    InstagramStoryActivity activity;
    private CommonClassForAPI CallInstaApi;
    StoriesListAdapter storiesListAdapter;
    UserListAdapter userListAdapter;


    DisposableObserver<FullDetailModel> storyDetailObserver = new DisposableObserver<FullDetailModel>() {
        @Override
        public void onNext(FullDetailModel fullDetailModel) {
            RVUserList.setVisibility(View.VISIBLE);
            pr_loading_bar.setVisibility(View.GONE);
            try {
                if (fullDetailModel.getReel_feed() != null) {
                    storiesListAdapter = new StoriesListAdapter(activity, fullDetailModel.getReel_feed().get(0).getItems(), activity);
                    RVStories.setAdapter(storiesListAdapter);
                    storiesListAdapter.notifyDataSetChanged();
                } else {
                    storiesListAdapter = new StoriesListAdapter(activity, new ArrayList<>(), activity);
                    RVStories.setAdapter(storiesListAdapter);
                    storiesListAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.d("TAG", "onNextsssss: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            pr_loading_bar.setVisibility(View.GONE);
            Log.d("TAG", "onErrorssss: " + e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            pr_loading_bar.setVisibility(View.GONE);
        }
    };

    private DisposableObserver<StoryModel> storyObserver = new DisposableObserver<StoryModel>() {
        @Override
        public void onNext(StoryModel storyModel) {
            RVUserList.setVisibility(View.VISIBLE);
            pr_loading_bar.setVisibility(View.GONE);
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < storyModel.getTray().size(); i++) {
                    try {
                        if (storyModel.getTray().get(i).getUser().getFull_name() != null) {
                            arrayList.add(storyModel.getTray().get(i));
                        }
                    } catch (Exception unused) {
                    }
                }
                userListAdapter = new UserListAdapter(activity, arrayList, new UserListInterface() {
                    @Override
                    public void userListClick(int i, TrayModel trayModel) {
                        callStoriesDetailApi(String.valueOf(trayModel.getUser().getPk()));
                    }

                    @Override
                    public void fbUserListClick(int position, NodeModel nodeModel) {

                    }
                });
                RVUserList.setAdapter(userListAdapter);
            } catch (Exception e) {
                Log.d("TAG", "onNext: " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            pr_loading_bar.setVisibility(View.GONE);
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            pr_loading_bar.setVisibility(View.GONE);
        }
    };

    private void callStoriesDetailApi(String str) {
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                CommonClass.showToast(activity, getResources().getString(R.string.no_net_conn));
            } else if (CallInstaApi != null) {
                pr_loading_bar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<FullDetailModel> disposableObserver = storyDetailObserver;
                commonClassForAPI2.getFullDetailFeed(disposableObserver, str, "ds_user_id=" + SharedPre.getInstance(activity).sharedGetString(activity, SharedPre.USERID) + "; sessionid=" + SharedPre.getInstance(activity).sharedGetString(activity, SharedPre.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_story);

        activity = this;
        CallInstaApi = CommonClassForAPI.getInstance();

        back = findViewById(R.id.back);
        instagram_story_text = findViewById(R.id.instagram_story_text);
        tvViewStories = findViewById(R.id.tvViewStories);
        pr_loading_bar = findViewById(R.id.pr_loading_bar);
        RVUserList = findViewById(R.id.RVUserList);
        RVStories = findViewById(R.id.RVStories);
        switchCompat = findViewById(R.id.Switch);
        private_account = findViewById(R.id.private_account);

        instagram_story_text.setSelected(true);
        private_account.setSelected(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCompat.setChecked(false);
                if (!SharedPre.getInstance(activity).sharedGetBoolean(activity, SharedPre.ISINSTALOGIN)) {
                    startActivity(new Intent(activity, InstagramLogin.class));
                    return;
                }
                AlertDialog delete_dialog = new AlertDialog.Builder(activity, R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.login_dailog, null);
                delete_dialog.setView(view1);
                delete_dialog.setCanceledOnTouchOutside(false);
                TextView yes = view1.findViewById(R.id.yes);
                TextView cancel = view1.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                        switchCompat.setChecked(true);
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPre.getInstance(activity).sharedPutBoolean(activity, SharedPre.ISINSTALOGIN, false);
                        SharedPre.getInstance(activity).sharedPutString(activity, SharedPre.COOKIES, "");
                        SharedPre.getInstance(activity).sharedPutString(activity, SharedPre.CSRF, "");
                        SharedPre.getInstance(activity).sharedPutString(activity, SharedPre.SESSIONID, "");
                        SharedPre.getInstance(activity).sharedPutString(activity, SharedPre.USERID, "");
                        if (SharedPre.getInstance(activity).sharedGetBoolean(activity, SharedPre.ISINSTALOGIN)) {
                            switchCompat.setChecked(true);
                        } else {
                            switchCompat.setChecked(false);
                            RVUserList.setVisibility(View.GONE);
                            RVStories.setVisibility(View.GONE);
                            tvViewStories.setTextColor(getResources().getColor(R.color.private_color));
                            tvViewStories.setText(activity.getResources().getText(R.string.view_stories));
                            tvViewStories.setBackgroundColor(activity.getColor(R.color.transparent));
                        }
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
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(activity, 2);
        RVStories.setLayoutManager(gridLayoutManager2);
        RVStories.setNestedScrollingEnabled(false);
        RVStories.setHasFixedSize(false);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
    }

    private void callStoriesApi() {
        try {
            if (!new CommonClass(activity).isNetworkAvailable()) {
                CommonClass.showToast(activity, getResources().getString(R.string.no_net_conn));
            } else if (CallInstaApi != null) {
                pr_loading_bar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<StoryModel> disposableObserver = storyObserver;
                commonClassForAPI2.getStories(disposableObserver, "ds_user_id=" + SharedPre.getInstance(activity).sharedGetString(activity, SharedPre.USERID) + "; sessionid=" + SharedPre.getInstance(activity).sharedGetString(activity, SharedPre.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void layoutCondition() {
        tvViewStories.setText(getResources().getString(R.string.stories));
        tvViewStories.setBackground(getDrawable(R.drawable.insta_story__bg));
        tvViewStories.setTextColor(getColor(R.color.white));
    }

    @Override
    protected void onResume() {
        super.onResume();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        if (SharedPre.getInstance(activity).sharedGetBoolean(activity, SharedPre.ISINSTALOGIN)) {
            layoutCondition();
            callStoriesApi();
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}