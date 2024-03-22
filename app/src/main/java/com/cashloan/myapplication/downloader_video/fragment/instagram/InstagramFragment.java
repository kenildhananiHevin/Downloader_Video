package com.cashloan.myapplication.downloader_video.fragment.instagram;

import static com.cashloan.myapplication.downloader_video.other.CommonClass.mEnstagramVideoPathDirectory;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.showToast;
import static com.cashloan.myapplication.downloader_video.other.SharedPre.ISINSTALOGIN;
import static com.cashloan.myapplication.downloader_video.other.SharedPre.SESSIONID;
import static com.cashloan.myapplication.downloader_video.other.SharedPre.USERID;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.adapter.StoriesListAdapter;
import com.cashloan.myapplication.downloader_video.adapter.UserListAdapter;
import com.cashloan.myapplication.downloader_video.instagram.api.CommonClassForAPI;
import com.cashloan.myapplication.downloader_video.instagram.other.InstagramDownloadTask;
import com.cashloan.myapplication.downloader_video.instagram.other.InstagramLogin;
import com.cashloan.myapplication.downloader_video.interfaces.UserListInterface;
import com.cashloan.myapplication.downloader_video.model.facebook_model.NodeModel;
import com.cashloan.myapplication.downloader_video.model.insta_model.InstagramDetailModel;
import com.cashloan.myapplication.downloader_video.model.insta_model.InstagramResponseModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.FullDetailModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.StoryModel;
import com.cashloan.myapplication.downloader_video.model.insta_story.TrayModel;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.cashloan.myapplication.downloader_video.other.SharedPre;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class InstagramFragment extends Fragment {
    EditText instagram_text;
    TextView download, paste, tvViewStories, private_account, download_text;
    SwitchCompat switchCompat;
    private String tempUrl = "";
    private CommonClassForAPI CallInstaApi;
    ArrayList<InstagramDetailModel> list = new ArrayList<>();
    private String mMediaTypeImage = null;
    private String mMediaTypeVideo = null;
    ProgressBar pr_loading_bar;
    RecyclerView RVUserList, RVStories;
    StoriesListAdapter storiesListAdapter;
    UserListAdapter userListAdapter;

    private BottomSheetDialog bottomSheetDialog;
    ImageView thumbvideoimageID;
    ConstraintLayout download_layout;
    LottieAnimationView progress;


    DisposableObserver<FullDetailModel> storyDetailObserver = new DisposableObserver<FullDetailModel>() {
        @Override
        public void onNext(FullDetailModel fullDetailModel) {
            RVUserList.setVisibility(View.VISIBLE);
            pr_loading_bar.setVisibility(View.GONE);
            try {
                if (fullDetailModel.getReel_feed() != null) {
                    storiesListAdapter = new StoriesListAdapter(requireActivity(), fullDetailModel.getReel_feed().get(0).getItems(), requireActivity());
                    RVStories.setAdapter(storiesListAdapter);
                    storiesListAdapter.notifyDataSetChanged();
                } else {
                    storiesListAdapter = new StoriesListAdapter(requireActivity(), new ArrayList<>(), requireActivity());
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
                userListAdapter = new UserListAdapter(requireActivity(), arrayList, new UserListInterface() {
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


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instagram, container, false);

        instagram_text = view.findViewById(R.id.instagram_text);
        download = view.findViewById(R.id.download);
        paste = view.findViewById(R.id.paste);
        tvViewStories = view.findViewById(R.id.tvViewStories);
        pr_loading_bar = view.findViewById(R.id.pr_loading_bar);
        RVUserList = view.findViewById(R.id.RVUserList);
        RVStories = view.findViewById(R.id.RVStories);
        switchCompat = view.findViewById(R.id.Switch);
        private_account = view.findViewById(R.id.private_account);

        download.setSelected(true);
        paste.setSelected(true);
        private_account.setSelected(true);

        CallInstaApi = CommonClassForAPI.getInstance();

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                instagram_text.setText(clipboardManager.getText());

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInstaVideo(instagram_text.getText().toString());
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISINSTALOGIN)) {
                    startActivity(new Intent(requireActivity(), InstagramLogin.class));
                    return;
                }
                AlertDialog delete_dialog = new AlertDialog.Builder(requireActivity(), R.style.MyTransparentBottomSheetDialogTheme).create();
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
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPre.getInstance(requireActivity()).sharedPutBoolean(requireActivity(), SharedPre.ISINSTALOGIN, false);
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.COOKIES, "");
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.CSRF, "");
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.SESSIONID, "");
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.USERID, "");
                        if (SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISINSTALOGIN)) {
                            switchCompat.setChecked(true);
                        } else {
                            switchCompat.setChecked(false);
                            RVUserList.setVisibility(View.GONE);
                            RVStories.setVisibility(View.GONE);
                            tvViewStories.setTextColor(getResources().getColor(R.color.private_color));
                            tvViewStories.setText(requireActivity().getResources().getText(R.string.view_stories));
                            tvViewStories.setBackgroundColor(requireActivity().getColor(R.color.transparent));
                        }
                        delete_dialog.dismiss();
                    }
                });

                delete_dialog.show();
                Window window = delete_dialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.80);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        });
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(requireActivity(), 2);
        RVStories.setLayoutManager(gridLayoutManager2);
        RVStories.setNestedScrollingEnabled(false);
        RVStories.setHasFixedSize(false);
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);

       /* if (intent.getStringExtra("MediaUrl") != null) {
            instagram_text.setText(intent.getStringExtra("MediaUrl"));
            DownloadInstaVideo(intent.getStringExtra("MediaUrl"));
        }*/

        return view;
    }


    private void callStoriesDetailApi(String str) {
        try {
            if (!new CommonClass(requireActivity()).isNetworkAvailable()) {
                CommonClass.showToast(requireActivity(), getResources().getString(R.string.no_net_conn));
            } else if (CallInstaApi != null) {
                pr_loading_bar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<FullDetailModel> disposableObserver = storyDetailObserver;
                commonClassForAPI2.getFullDetailFeed(disposableObserver, str, "ds_user_id=" + SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.USERID) + "; sessionid=" + SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callStoriesApi() {
        try {
            if (!new CommonClass(requireActivity()).isNetworkAvailable()) {
                CommonClass.showToast(requireActivity(), getResources().getString(R.string.no_net_conn));
            } else if (CallInstaApi != null) {
                pr_loading_bar.setVisibility(View.VISIBLE);
                CommonClassForAPI commonClassForAPI2 = CallInstaApi;
                DisposableObserver<StoryModel> disposableObserver = storyObserver;
                commonClassForAPI2.getStories(disposableObserver, "ds_user_id=" + SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.USERID) + "; sessionid=" + SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.SESSIONID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void layoutCondition() {
        tvViewStories.setText(requireActivity().getResources().getString(R.string.stories));
        tvViewStories.setBackground(requireActivity().getDrawable(R.drawable.insta_story__bg));
        tvViewStories.setTextColor(requireActivity().getColor(R.color.white));
    }

    private void DownloadInstaVideo(String obj) {
        if (obj.equals("")) {
            showToast(requireActivity(), getResources().getString(R.string.enter_url));
        } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
            showToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
        } else {
            if (SharedPre.sharedGetBoolean(requireActivity(), ISINSTALOGIN)) {
                try {
                    if (!mEnstagramVideoPathDirectory.exists()) {
                        mEnstagramVideoPathDirectory.mkdirs();
                    }
                    if (new URL(instagram_text.getText().toString()).getHost().equals("www.instagram.com")) {
                        tempUrl = instagram_text.getText().toString();
                        String str2 = getUrlWithoutParameters(tempUrl) + "?__a=1&__d=dis";
                        CallInstaApi.callResult(getEnstaUserName, str2, "ds_user_id=" + SharedPre.sharedGetString(requireActivity(), USERID) + "; sessionid=" + SharedPre.sharedGetString(requireActivity(), SESSIONID));
                    } else {
                        showToast(requireActivity(), getString(R.string.enter_valid_url));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                startActivity(new Intent(requireActivity(), InstagramLogin.class));
               /* AlertDialog dialog = new AlertDialog.Builder(requireActivity(), R.style.MyTransparentBottomSheetDialogTheme).create();
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.login_dailog, null);
                dialog.setView(view);
                dialog.setCanceledOnTouchOutside(false);

                TextView dialogMainTitleID =  view.findViewById(R.id.dialogMainTitleID);
                TextView dialogIntroText =  view.findViewById(R.id.dialogIntroText);
                TextView TvYesLoginLogoutID =  view.findViewById(R.id.TvYesLoginLogoutID);
                TextView TvCancelID =  view.findViewById(R.id.TvCancelID);

                dialogMainTitleID.setText(getResources().getString(R.string.login_of_your_account));
                dialogIntroText.setText(getResources().getString(R.string.login_of_your_account2));
                TvYesLoginLogoutID.setText(getResources().getString(R.string.login_instagram));

                TvYesLoginLogoutID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(requireActivity(), InstagramLogin.class));
                        dialog.dismiss();
                    }
                });

                TvCancelID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int dialogWidth = (int) (screenWidth * 0.80);
                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);*/
            }
        }
    }

    private DisposableObserver<JsonObject> getEnstaUserName = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject jsonObject) {
            int i;
            try {
                list.clear();
                JSONObject json = new JSONObject(jsonObject.toString());
                JSONArray itemsArray = json.getJSONArray("items");
                String userNametemp = "";
                for (int i2 = 0; i2 < itemsArray.length(); i2++) {
                    JSONObject itemObject = itemsArray.getJSONObject(i2);
                    int mediaType = itemObject.getInt("media_type");
                    userNametemp = itemObject.getJSONObject("user").getString("username");
                    String str = "url";
                    if (mediaType == 1) {
                        JSONArray candidatesArray = itemObject.getJSONObject("image_versions2").getJSONArray("candidates");
                        for (int i4 = 0; i4 < candidatesArray.length(); i4++) {
                            JSONObject candidateObject = candidatesArray.getJSONObject(i4);
                            InstagramDetailModel instagramDetailModel = new InstagramDetailModel();
                            instagramDetailModel.width = candidateObject.getInt("width");
                            instagramDetailModel.height = candidateObject.getInt("height");
                            instagramDetailModel.url = candidateObject.getString(str);
                            instagramDetailModel.isFlag = (i4 == 0);
                            list.add(instagramDetailModel);
                        }
                        i = i2;
                    } else {
                        i = i2;
                        if (mediaType == 2) {
                            JSONArray videoVersionsArray = itemObject.getJSONArray("video_versions");
                            for (int i7 = 0; i7 < videoVersionsArray.length(); i7++) {
                                JSONObject videoObject = videoVersionsArray.getJSONObject(i7);
                                InstagramDetailModel instagramDetailModel2 = new InstagramDetailModel();
                                instagramDetailModel2.width = videoObject.getInt("width");
                                instagramDetailModel2.height = videoObject.getInt("height");
                                instagramDetailModel2.url = videoObject.getString(str);
                                instagramDetailModel2.isFlag = (i7 == 0);
                                list.add(instagramDetailModel2);
                            }
                        } else {
                            showToast(requireActivity(), getResources().getString(R.string.somethingWentWrong));
                        }
                    }
                    i2 = i + 1;
                }
                /*dialogInstagramDownload(userNametemp, list.get(0).url);*/
                if (list.size() == 0) {
                    showToast(requireActivity(), getResources().getString(R.string.somethingWentWrong));
                }
                bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialog);
                bottomSheetDialog.setContentView(R.layout.instagram_bottom_show);
                thumbvideoimageID = bottomSheetDialog.findViewById(R.id.thumbvideoimageID);
                download_text = bottomSheetDialog.findViewById(R.id.download_text);
                download_layout = bottomSheetDialog.findViewById(R.id.download_layout);
                progress = bottomSheetDialog.findViewById(R.id.progress);

                Glide.with(requireActivity())
                        .load(list.get(0).url)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(thumbvideoimageID);

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        bottomSheetDialog.dismiss();
                    }
                });

                try {
                    download_text.setText(R.string.download);
                    download_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startDownload(list.get(0).getUrl(), requireActivity(), getVideoFilenameFromURL(list.get(0).getUrl()));
                            bottomSheetDialog.dismiss();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bottomSheetDialog.show();
//                startDownload(list.get(0).getUrl(), requireActivity(), getVideoFilenameFromURL(list.get(0).getUrl()));
            } catch (Exception e) {
                e.printStackTrace();
                String str2 = getUrlWithoutParameters(tempUrl) + "?__a=1&__d=dis";
                try {
                    CallInstaApi.callResult(instaObserver, str2, "ds_user_id=" + SharedPre.sharedGetString(requireActivity(), USERID) + "; " + "sessionid=" + SharedPre.sharedGetString(requireActivity(), SESSIONID));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        @Override
        public void onError(Throwable th) {
            th.printStackTrace();
            String str = getUrlWithoutParameters(tempUrl) + "?__a=1&__d=dis";
            try {
                CallInstaApi.callResult(instaObserver, str, "ds_user_id=" + SharedPre.sharedGetString(requireActivity(), USERID) + "; " + "sessionid=" + SharedPre.sharedGetString(requireActivity(), SESSIONID));
            } catch (Exception e) {
                showToast(requireActivity(), getResources().getString(R.string.somethingWentWrong));
            }
        }

        @Override
        public void onComplete() {
        }
    };

/*
    private void dialogInstagramDownload(String userNametemp, String url) {
        mediaUserID.setVisibility(View.VISIBLE);
        mediaPreviewImageID.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);

        Glide.with(requireActivity())
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mediaPreviewImageID);
        mediaUserID.setText("@" + userNametemp);
    }
*/

    private DisposableObserver<JsonObject> instaObserver = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject jsonObject) {
            try {
                InstagramResponseModel responseModel = new Gson().fromJson(jsonObject.toString(), new TypeToken<InstagramResponseModel>() {
                }.getType());
                InstagramResponseModel.ApplyChildView edgeSidecarChildren = responseModel.getGraphql().getShortcodeMedia().getEdgeSidecarToChildren();
                if (edgeSidecarChildren != null) {
                    List<InstagramResponseModel.ApplyChildView.Edge> edges = edgeSidecarChildren.getEdges();
                    for (int i = 0; i < edges.size(); i++) {
                        if (edges.get(i).getNode().isVideo()) {
                            mMediaTypeVideo = edges.get(i).getNode().getVideoUrl();
                            String str = mMediaTypeVideo;
                            startDownload(str, requireActivity(), getVideoFilenameFromURL(mMediaTypeVideo));
                            instagram_text.setText("");
                            mMediaTypeVideo = "";
                        } else {
                            mMediaTypeImage = edges.get(i).getNode().getDisplayResources().get(edges.get(i).getNode().getDisplayResources().size() - 1).getSrc();
                            String str3 = mMediaTypeImage;
                            startDownload(str3, requireActivity(), getImageFilenameFromURL(mMediaTypeImage));
                            mMediaTypeImage = "";
                            instagram_text.setText("");
                        }
                    }
                } else if (responseModel.getGraphql().getShortcodeMedia().isVideo()) {
                    mMediaTypeVideo = responseModel.getGraphql().getShortcodeMedia().getVideoUrl();
                    String str5 = mMediaTypeVideo;
                    startDownload(str5, requireActivity(), getVideoFilenameFromURL(mMediaTypeVideo));
                    mMediaTypeVideo = "";
                    instagram_text.setText("");
                } else {
                    mMediaTypeImage = responseModel.getGraphql().getShortcodeMedia().getDisplayResources().get(responseModel.getGraphql().getShortcodeMedia().getDisplayResources().size() - 1).getSrc();
                    String str7 = mMediaTypeImage;
                    startDownload(str7, requireActivity(), getImageFilenameFromURL(mMediaTypeImage));
                    mMediaTypeImage = "";
                    instagram_text.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast(requireActivity(), getResources().getString(R.string.logout_instagram));
            }
        }

        @Override
        public void onError(Throwable th) {
            showToast(requireActivity(), getResources().getString(R.string.logout_instagram));
        }

        @Override
        public void onComplete() {
        }
    };

    public static void startDownload(String str, FragmentActivity enstagramHomeActivity, String str3) {
        new InstagramDownloadTask(enstagramHomeActivity, str3).execute(str);
    }

    public static String getVideoFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath()).getName();
        } catch (MalformedURLException e) {
            return System.currentTimeMillis() + ".mp4";
        }
    }

    public static String getImageFilenameFromURL(String str) {
        try {
            return new File(new URL(str).getPath()).getName();
        } catch (MalformedURLException e) {
            return System.currentTimeMillis() + ".jpg";
        }
    }

    public String getUrlWithoutParameters(String str) {
        try {
            URI uri = new URI(str);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            showToast(requireActivity(), getResources().getString(R.string.enter_valid_url));
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        if (SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISINSTALOGIN)) {
            layoutCondition();
            callStoriesApi();
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
    }
}