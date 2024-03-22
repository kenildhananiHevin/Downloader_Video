package com.cashloan.myapplication.downloader_video.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.fragment.whatsapp.MediaDownloadFragment;
import com.cashloan.myapplication.downloader_video.model.DownloadedMediaInfoModel;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.cashloan.myapplication.downloader_video.whatsapp.MediaWpImageViewerActivity;
import com.cashloan.myapplication.downloader_video.whatsapp.MediaWpVIdeoViewerActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;

public class DownloadedWhatsappVideoListAdapter extends RecyclerView.Adapter<DownloadedWhatsappVideoListAdapter.ViewHolder> {
    FragmentActivity requireActivity;
    ArrayList<DownloadedMediaInfoModel> fileArrayList;
    MediaDownloadFragment mediaDownloadFragment;

    public DownloadedWhatsappVideoListAdapter(FragmentActivity requireActivity, ArrayList<DownloadedMediaInfoModel> fileArrayList, MediaDownloadFragment mediaDownloadFragment) {
        this.requireActivity = requireActivity;
        this.fileArrayList = fileArrayList;
        this.mediaDownloadFragment = mediaDownloadFragment;
    }

    @NonNull
    @Override
    public DownloadedWhatsappVideoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_download_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedWhatsappVideoListAdapter.ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        DownloadedMediaInfoModel file = fileArrayList.get(position);
        Log.d("TAG", "onBindViewHolderssss: " + file.getMediaFile().getPath());
        if (file.getMediaFile() != null && file.getMediaFile().getName().endsWith(".mp4")) {
            holder.play.setVisibility(View.VISIBLE);
        } else {
            holder.play.setVisibility(View.GONE);
        }

        Glide.with(requireActivity).setDefaultRequestOptions(new RequestOptions()).load(file.getMediaFile())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.picImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.getMediaFile() != null && file.getMediaFile().getName().endsWith(".mp4")) {
                    VideoShow(file, 0);
                } else {
                    ImageShow(file, 0);
                }
            }
        });

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                AlertDialog delete_dialog = new AlertDialog.Builder(requireActivity, R.style.MyTransparentBottomSheetDialogTheme).create();
//                LayoutInflater layoutInflater = requireActivity.getLayoutInflater();
//                View view1 = layoutInflater.inflate(R.layout.dialog_delete, null);
//                delete_dialog.setView(view1);
//                delete_dialog.setCanceledOnTouchOutside(false);
//                TextView cancel = view1.findViewById(R.id.delete_cancel);
//                TextView delete_btn = view1.findViewById(R.id.delete_ok);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        delete_dialog.dismiss();
//                    }
//                });
//
//                delete_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteData.deleteclick(file.getMediaFile(), position);
//                        delete_dialog.dismiss();
//                    }
//                });
//
//                delete_dialog.show();
//                Window window = delete_dialog.getWindow();
//                DisplayMetrics displayMetrics = new DisplayMetrics();
//                requireActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//                int screenWidth = displayMetrics.widthPixels;
//                int dialogWidth = (int) (screenWidth * 0.80);
//                window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//                window.setGravity(Gravity.CENTER);
//            }
//        });

//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (file.getMediaFile() != null && file.getMediaFile().getName().endsWith(".mp4")) {
//                    CommonClass.shareVideo(requireActivity,file.getMediaFile().getAbsolutePath());
//                } else {
//                    CommonClass.shareImage(requireActivity,file.getMediaFile().getAbsolutePath());
//                }
//            }
//        });

    }

    private void ImageShow(DownloadedMediaInfoModel file, int i) {
        Intent intent = new Intent(requireActivity, MediaWpImageViewerActivity.class);
        intent.putExtra("image", file.getMediaFile().toString());
        intent.putExtra("type", "image");
        intent.putExtra("pack", file.getPack());
        intent.putExtra("name", file.getMediaName());
        requireActivity.startActivity(intent);
    }

    private void VideoShow(DownloadedMediaInfoModel file, int i) {
        if (i == 0) {
            Intent intent = new Intent(requireActivity, MediaWpVIdeoViewerActivity.class);
            intent.putExtra("video", file.getMediaFile().toString());
            intent.putExtra("type", "video");
            intent.putExtra("pack", file.getPack());
            intent.putExtra("name", file.getMediaName());
            requireActivity.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView picImg;
        ImageView play;
        LottieAnimationView progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picImg = itemView.findViewById(R.id.picImg);
            play = itemView.findViewById(R.id.play);
            progress = itemView.findViewById(R.id.progress);
        }
    }
}
