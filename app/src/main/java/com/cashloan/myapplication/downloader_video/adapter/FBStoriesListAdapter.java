package com.cashloan.myapplication.downloader_video.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.instagram.InstaStoryImageShowActivity;
import com.cashloan.myapplication.downloader_video.instagram.InstaStoryVideoShowActivity;
import com.cashloan.myapplication.downloader_video.model.facebook_model.NodeModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class FBStoriesListAdapter extends RecyclerView.Adapter<FBStoriesListAdapter.ViewHolder> {
    Context context;
    ArrayList<NodeModel> items;
    FragmentActivity activity;
    private NodeModel modelNodeDum;

    public FBStoriesListAdapter(Context context, ArrayList<NodeModel> items, FragmentActivity activity, NodeModel modelNodeDum) {
        this.context = context;
        this.items = items;
        this.activity = activity;
        this.modelNodeDum = modelNodeDum;
    }

    @NonNull
    @Override
    public FBStoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fb_story_show, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FBStoriesListAdapter.ViewHolder holder, int position) {
        NodeModel itemModel = items.get(position);
        try {
            if (itemModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().get__typename().equalsIgnoreCase("Video")) {
                holder.fb_play_video_icon.setVisibility(View.VISIBLE);
            } else {
                holder.fb_play_video_icon.setVisibility(View.GONE);
            }
            Glide.with(context).load(itemModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPreviewImage().get("uri").getAsString()).thumbnail(0.2f).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().get__typename().equalsIgnoreCase("Video")) {
                    VideoShow(itemModel);
                } else {
                    ImageShow(itemModel);
                }
            }
        });
/*        holder.relativeLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FBStoryPreviewActivity.class);
                intent.putExtra("ImageTrayName", modelNodeDum.getNodeDataModel().getStory_bucket_owner().get("name").getAsString());
                intent.putExtra("ImageTrayFile", modelNodeDum.getNodeDataModel().getOwner().getAsJsonObject("profile_picture").get("uri").getAsString());
                intent.putExtra("Position", position);
                context.startActivity(intent);

            }
        });*/
    }

    private void ImageShow(NodeModel itemModel) {
        Intent intent = new Intent(activity, InstaStoryImageShowActivity.class);
        intent.putExtra("image", itemModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPreviewImage().get("uri").getAsString());
        intent.putExtra("type", "image");
        intent.putExtra("pack", itemModel.getPack());
        intent.putExtra("from","facebook");
        activity.startActivity(intent);
    }

    private void VideoShow(NodeModel itemModel) {
        Intent intent = new Intent(activity, InstaStoryVideoShowActivity.class);
        intent.putExtra("video", itemModel.getNodeDataModel().getAttachmentsList().get(0).getMediaDataModel().getPlayable_url_quality_hd());
        intent.putExtra("type", "video");
        intent.putExtra("pack", itemModel.getPack());
        intent.putExtra("from","facebook");
        activity.startActivity(intent);

    }


    @Override
    public int getItemCount() {
        ArrayList<NodeModel> arrayList = items;
        if (arrayList == null) {
            return 0;
        }

        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fb_play_video_icon;
        ShapeableImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fb_play_video_icon = itemView.findViewById(R.id.fb_play_video_icon);
            image = itemView.findViewById(R.id.image);
        }
    }
}
