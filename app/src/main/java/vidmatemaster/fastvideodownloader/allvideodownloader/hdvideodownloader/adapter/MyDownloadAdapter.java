package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.frame.FrameActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.my_download.MyDownload;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload.MyDownloadActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.mydownload.SaveImageShowActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MyDownloadAdapter extends RecyclerView.Adapter<MyDownloadAdapter.ViewHolder> {
    MyDownloadActivity activity;
    ArrayList<MyDownload> list;

    public MyDownloadAdapter(MyDownloadActivity activity, ArrayList<MyDownload> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.my_download_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyDownloadAdapter.ViewHolder holder, int position) {
        MyDownload myDownload = list.get(position);
        holder.insta_text.setText(myDownload.getName());
        holder.insta_items.setText(myDownload.getFiles().size() + " " + activity.getString(R.string.items));
        if (!myDownload.getFiles().isEmpty() && myDownload.getFiles().get(0).getAbsolutePath() != null) {
            Glide.with(activity).load(myDownload.getFiles().get(0).getAbsolutePath()).into(holder.image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDownload.getName().equals("SavedPhotos")){
                    Intent intent = new Intent(activity, SaveImageShowActivity.class);
                    activity.startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, FrameActivity.class);
                    intent.putExtra("from", myDownload.getName());
                    activity.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView image;
        TextView insta_items, insta_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            insta_items = itemView.findViewById(R.id.insta_items);
            insta_text = itemView.findViewById(R.id.insta_text);
        }
    }
}
