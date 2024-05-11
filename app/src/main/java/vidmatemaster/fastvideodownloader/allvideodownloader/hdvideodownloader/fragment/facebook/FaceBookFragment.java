package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.facebook;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass.showToast;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.ObjectJson;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.FBStoriesListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.FbStoryUserListAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.FBLoginActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other.DataDownloadHelper;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other.DownloadServiceLinkNew;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other.OtherFuntions;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.api.CommonClassForAPI;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.interfaces.UserListInterface;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.facebook_model.EdgesModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.facebook_model.NodeModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.TrayModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class FaceBookFragment extends Fragment {

    String strName = "facebook";
    String strName1 = "fb.watch";
    String strName2 = "fb.gg";

    private DownloadServiceLinkNew mediadownloadLinkHandeler;
    private DataDownloadHelper mediadownloadHelper;

    private BottomSheetDialog bottomSheetDialog;

    EditText facebook_text;
    TextView facebook_download, facebook_paste, tvViewStories, private_account, lowqulitytitleID, lowvideosizeID, highqulitytitleID, highvideosizeID;
    SwitchCompat switchCompat;
    RecyclerView RVUserList, RVStories;
    ProgressBar pr_loading_bar;
    CommonClassForAPI commonClassForAPI;
    ArrayList<NodeModel> edgeModelList = new ArrayList<>();
    FBStoriesListAdapter fbStoriesListAdapter;
    FbStoryUserListAdapter fbStoryUserListAdapter;
    ImageView thumbvideoimageID;
    ConstraintLayout sd_layout, hd_layout;
    NodeModel modelNodeDum;
    LottieAnimationView progress;

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_book, container, false);

        facebook_text = view.findViewById(R.id.facebook_text);
        facebook_download = view.findViewById(R.id.download);
        facebook_paste = view.findViewById(R.id.paste);
        tvViewStories = view.findViewById(R.id.tvViewStories);
        pr_loading_bar = view.findViewById(R.id.pr_loading_bar);
        RVUserList = view.findViewById(R.id.RVUserList);
        RVStories = view.findViewById(R.id.RVStories);
        switchCompat = view.findViewById(R.id.Switch);
        private_account = view.findViewById(R.id.private_account);


        facebook_download.setSelected(true);
        facebook_paste.setSelected(true);
        private_account.setSelected(true);

        commonClassForAPI = CommonClassForAPI.getInstance();

        mediadownloadLinkHandeler = new DownloadServiceLinkNew(requireActivity());
        mediadownloadHelper = new DataDownloadHelper(requireActivity());

        facebook_paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                facebook_text.setText(clipboardManager.getText());
            }
        });

        facebook_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String obj = facebook_text.getText().toString();
                if (obj.equals("")) {
                    showToast(requireActivity(), getString(R.string.paste_a_video_url_to_download_the_post));
                } else if (!Patterns.WEB_URL.matcher(obj).matches()) {
                    showToast(requireActivity(), getString(R.string.not_a_valid_link));
                } else {
                    try {
                        URL url = new URL(facebook_text.getText().toString());
                        if (url.getHost().equals("www.facebook.com")) {
                            new OtherFuntions(requireActivity());
                            mediadownloadLinkHandeler.links(obj);
                            bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialog);
                            bottomSheetDialog.setContentView(R.layout.link_gen);
                            thumbvideoimageID = bottomSheetDialog.findViewById(R.id.thumbvideoimageID);
                            lowqulitytitleID = bottomSheetDialog.findViewById(R.id.lowqulitytitleID);
                            lowvideosizeID = bottomSheetDialog.findViewById(R.id.lowvideosizeID);
                            sd_layout = bottomSheetDialog.findViewById(R.id.sd_layout);
                            highqulitytitleID = bottomSheetDialog.findViewById(R.id.highqulitytitleID);
                            highvideosizeID = bottomSheetDialog.findViewById(R.id.highvideosizeID);
                            hd_layout = bottomSheetDialog.findViewById(R.id.hd_layout);
                            progress = bottomSheetDialog.findViewById(R.id.progress);
                            bottomSheetDialog.show();
                        } else {
                            showToast(requireActivity(), getString(R.string.paste_a_facebook_link));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCompat.setChecked(false);
                if (!SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISFBLOGIN)) {
                    startActivity(new Intent(requireActivity(), FBLoginActivity.class));
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
                        switchCompat.setChecked(true);
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPre.getInstance(requireActivity()).sharedPutBoolean(requireActivity(), SharedPre.ISFBLOGIN, false);
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.FBKEY, "");
                        SharedPre.getInstance(requireActivity()).sharedPutString(requireActivity(), SharedPre.FBCOOKIES, "");
                        if (SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISFBLOGIN)) {
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
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fesakevent(ObjectJson event) {
        JSONObject message = event.getJsonObject();
        try {
            JSONObject jSONObject2 = message.getJSONObject("sd");
            String str = "video" + new Random().nextInt() + "_sd.mp4";
            String link = jSONObject2.getString("link");

            Glide.with(this)
                    .load(link)
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
                lowqulitytitleID.setText(R.string.medium);
                String numericPart = jSONObject2.getString("size").substring(0, jSONObject2.getString("size").length() - 3).trim();
                lowvideosizeID.setText(getString(R.string.size) + "  " + bToMB(numericPart));
                sd_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mediadownloadHelper.download(jSONObject2.getString("link"), str, true);
                            bottomSheetDialog.dismiss();
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            } catch (Exception unused) {
                sd_layout.setVisibility(View.GONE);
            }
            try {
                JSONObject jSONObject3 = message.getJSONObject("hd");
                String str2 = "video" + new Random().nextInt() + "_hd.mp4";
                highqulitytitleID.setText(R.string.high);
                String numericPart2 = jSONObject3.getString("size").substring(0, jSONObject3.getString("size").length() - 3).trim();
                highvideosizeID.setText(getString(R.string.size) + "  " + bToMB(numericPart2));
                hd_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mediadownloadHelper.download(jSONObject3.getString("link"), str2, true);
                            bottomSheetDialog.dismiss();
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
                hd_layout.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String bToMB(String sizeInMiB) {
        try {
            double sizeMiB = Double.parseDouble(sizeInMiB);
            double sizeMB = sizeMiB * 1.048576;
            String sizeInMB = String.format("%.1f", sizeMB);
            return sizeInMB + " MB";
        } catch (NumberFormatException e) {
            return "Invalid input";
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 1);
        RVUserList.setLayoutManager(gridLayoutManager);
        RVUserList.setNestedScrollingEnabled(false);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        fbStoryUserListAdapter = new FbStoryUserListAdapter(requireActivity(), edgeModelList, new UserListInterface() {
            @Override
            public void userListClick(int i, TrayModel trayModel) {
            }

            @Override
            public void fbUserListClick(int position, NodeModel nodeModel) {
                modelNodeDum = nodeModel;
                getFacebookUserStories(nodeModel.getNodeDataModel().getId());
            }
        });
        RVUserList.setAdapter(fbStoryUserListAdapter);
        if (SharedPre.getInstance(requireActivity()).sharedGetBoolean(requireActivity(), SharedPre.ISFBLOGIN)) {
            layoutCondition();
            getFacebookUserData();
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
    }

    private void getFacebookUserStories(String str) {
        pr_loading_bar.setVisibility(View.VISIBLE);
        ANRequest.PostRequestBuilder addBodyParameter = AndroidNetworking.post("https://www.facebook.com/api/graphql/")
                .addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7")
                .addHeaders("cookie", SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.FBCOOKIES))
                .addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter("fb_dtsg", SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.FBKEY));
        addBodyParameter.addBodyParameter("variables", "{\"bucketID\":\"" + str + "\",\"initialBucketID\":\"" + str + "\",\"initialLoad\":false,\"scale\":5}")
                .addBodyParameter("doc_id", "2558148157622405")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    public void onResponse(JSONObject jSONObject) {
                        PrintStream printStream = System.out;
                        printStream.println("JsonResp- " + jSONObject);
                        pr_loading_bar.setVisibility(View.GONE);
                        if (jSONObject != null) {
                            try {
                                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("bucket").getJSONObject("unified_stories");
                                EdgesModel edgesModel = (EdgesModel) new Gson().fromJson(String.valueOf(jSONObject2), new TypeToken<EdgesModel>() {
                                }.getType());
                                if (edgesModel.getEdgeModel().size() > 0) {
                                    edgesModel.getEdgeModel().get(0).getNodeDataModel().getAttachmentsList();
                                    fbStoriesListAdapter = new FBStoriesListAdapter(requireActivity(), edgesModel.getEdgeModel(), requireActivity(), modelNodeDum);
                                    RVStories.setAdapter(fbStoriesListAdapter);
                                    fbStoriesListAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(requireActivity(), "Unable to get stories!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    public void onError(ANError aNError) {
                        pr_loading_bar.setVisibility(View.GONE);
                    }
                });
    }

    private void getFacebookUserData() {
        pr_loading_bar.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://www.facebook.com/api/graphql/")
                .addHeaders("accept-language", "en,en-US;q=0.9,fr;q=0.8,ar;q=0.7")
                .addHeaders("cookie", SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.FBCOOKIES))
                .addHeaders("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter("fb_dtsg", SharedPre.getInstance(requireActivity()).sharedGetString(requireActivity(), SharedPre.FBKEY))
                .addBodyParameter("variables", "{\"bucketsCount\":200,\"initialBucketID\":null,\"pinnedIDs\":[\"\"],\"scale\":3}")
                .addBodyParameter("doc_id", "2893638314007950")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    public void onResponse(JSONObject jSONObject) {
                        PrintStream printStream = System.out;
                        printStream.println("JsonResp- " + jSONObject);
                        if (jSONObject != null) {
                            try {
                                JSONObject jSONObject2 = jSONObject.getJSONObject("data").getJSONObject("me").getJSONObject("unified_stories_buckets");
                                EdgesModel edgesModel = (EdgesModel) new Gson().fromJson(String.valueOf(jSONObject2), new TypeToken<EdgesModel>() {
                                }.getType());
                                if (edgesModel.getEdgeModel().size() > 0) {
                                    edgeModelList.clear();
                                    edgeModelList.addAll(edgesModel.getEdgeModel());
                                    fbStoryUserListAdapter.notifyDataSetChanged();
                                    edgeModelList.remove(0);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RVUserList.setVisibility(View.VISIBLE);
                        pr_loading_bar.setVisibility(View.GONE);

                    }

                    public void onError(ANError aNError) {
                        pr_loading_bar.setVisibility(View.GONE);
                    }
                });
    }

    private void layoutCondition() {
        tvViewStories.setText(requireActivity().getResources().getString(R.string.stories));
        tvViewStories.setBackground(requireActivity().getDrawable(R.drawable.insta_story__bg));
        tvViewStories.setTextColor(requireActivity().getColor(R.color.white));
    }
}