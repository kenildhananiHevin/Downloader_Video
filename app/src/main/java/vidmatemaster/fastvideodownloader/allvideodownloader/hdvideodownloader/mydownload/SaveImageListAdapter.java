package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.DownloadedMediaInfoModel;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp.MediaWpImageViewerActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.util.ArrayList;

public class SaveImageListAdapter extends RecyclerView.Adapter<SaveImageListAdapter.ViewHolder> {
    SaveImageShowActivity activity;
    ArrayList<DownloadedMediaInfoModel> list;
    Context saveImageShowActivity;
    DeleteData deleteData;

    public SaveImageListAdapter(SaveImageShowActivity activity, ArrayList<DownloadedMediaInfoModel> list, Context saveImageShowActivity, DeleteData deleteData) {
        this.activity = activity;
        this.list = list;
        this.saveImageShowActivity = saveImageShowActivity;
        this.deleteData = deleteData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_download_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveImageListAdapter.ViewHolder holder, int position) {
        DownloadedMediaInfoModel file = list.get(position);
        Log.d("TAG", "onBindViewHolderssss: " + file.getMediaFile().getPath());
        if (file.getMediaFile() != null && file.getMediaFile().getName().endsWith(".mp4")) {
            holder.play.setVisibility(View.VISIBLE);
        } else {
            holder.play.setVisibility(View.GONE);
        }

        Glide.with(activity).setDefaultRequestOptions(new RequestOptions()).load(file.getMediaFile())
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
                } else {
                    ImageShow(file, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void ImageShow(DownloadedMediaInfoModel file, int i) {
        Intent intent = new Intent(activity, MediaWpImageViewerActivity.class);
        intent.putExtra("image", file.getMediaFile().toString());
        intent.putExtra("type", "image");
        intent.putExtra("pack", file.getPack());
        intent.putExtra("name", file.getMediaName());
        activity.startActivity(intent);
    }


    public interface DeleteData {
        void deleteclick(File str, int i);
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
