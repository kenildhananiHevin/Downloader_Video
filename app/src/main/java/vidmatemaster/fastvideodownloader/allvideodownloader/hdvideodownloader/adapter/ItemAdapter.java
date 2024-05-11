package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity.MoreToolsActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.activity.VideoDownloaderActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.FaceBookActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.InstagramActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp.MediaWpStatusActivity;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    VideoDownloaderActivity videoDownloaderActivity;
    ArrayList<Pair> tools_list;

    public ItemAdapter(VideoDownloaderActivity videoDownloaderActivity, ArrayList<Pair> tools_list) {
        this.videoDownloaderActivity = videoDownloaderActivity;
        this.tools_list = tools_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tools_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Pair pair = tools_list.get(position);
        holder.textView.setSelected(true);
        holder.textView.setText(pair.second.toString());
        Glide.with(videoDownloaderActivity).load(pair.first).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    videoDownloaderActivity.showInterstitial(new Intent(videoDownloaderActivity, InstagramActivity.class));
                } else if (position == 1) {
                    videoDownloaderActivity.showInterstitial(new Intent(videoDownloaderActivity, FaceBookActivity.class));
                } else if (position == 2) {
                    Intent intent = new Intent(videoDownloaderActivity, MediaWpStatusActivity.class);
                    intent.putExtra("status_type", "wpstatus");
                    videoDownloaderActivity.showInterstitial(intent);
                } else if (position == 3) {
                    videoDownloaderActivity.showInterstitial(new Intent(videoDownloaderActivity, MoreToolsActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tools_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
