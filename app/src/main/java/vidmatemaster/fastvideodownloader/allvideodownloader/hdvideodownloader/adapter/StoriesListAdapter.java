package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

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
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.story.InstaStoryImageShowActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.story.InstaStoryVideoShowActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.ItemModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class StoriesListAdapter extends RecyclerView.Adapter<StoriesListAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemModel> items;
    FragmentActivity activity;

    public StoriesListAdapter(Context context, ArrayList<ItemModel> items, FragmentActivity activity) {
        this.context = context;
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.insta_story_click_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesListAdapter.ViewHolder holder, int position) {
        ItemModel itemModel = items.get(position);
        try {
            if (itemModel.getMedia_type() == 2) {
                holder.insta_play_video_icon.setVisibility(View.VISIBLE);
            } else {
                holder.insta_play_video_icon.setVisibility(View.GONE);
            }
            Glide.with(this.context).load(itemModel.getImage_versions2().getCandidates().get(0).getUrl()).into(holder.shapeableImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModel.getMedia_type() == 2) {
                    VideoShow(itemModel);
                } else {
                    ImageShow(itemModel);
                }
            }
        });
    }

    private void ImageShow(ItemModel items) {
        Intent intent = new Intent(activity, InstaStoryImageShowActivity.class);
        intent.putExtra("image", items.getImage_versions2().getCandidates().get(0).getUrl());
        intent.putExtra("type", "image");
        intent.putExtra("pack", items.getPack());
        intent.putExtra("from","instagram");
        activity.startActivity(intent);
    }

    private void VideoShow(ItemModel items) {
        Intent intent = new Intent(activity, InstaStoryVideoShowActivity.class);
        intent.putExtra("video", items.getVideo_versions().get(0).getUrl());
        intent.putExtra("type", "video");
        intent.putExtra("pack", items.getPack());
        intent.putExtra("from","instagram");
        activity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        ArrayList<ItemModel> arrayList = items;
        if (arrayList == null) {
            return 0;
        }

        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView shapeableImageView;
        ImageView insta_play_video_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shapeableImageView = itemView.findViewById(R.id.image);
            insta_play_video_icon = itemView.findViewById(R.id.insta_play_video_icon);


//            YoYo.with(Techniques.FadeInUp).duration(900).playOn(itemView.findViewById(R.id.rl_main));
        }
    }
}
