package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorShowActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.creator_model.DpCreator;

import java.util.ArrayList;

public class DpCreatorAdapter extends RecyclerView.Adapter<DpCreatorAdapter.ViewHolder> {
    DpCreatorActivity activity;
    ArrayList<DpCreator> list;

    public DpCreatorAdapter(DpCreatorActivity activity, ArrayList<DpCreator> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.dp_creator_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DpCreatorAdapter.ViewHolder holder, int position) {
        DpCreator dpCreator = list.get(position);
        Glide.with(activity).load(dpCreator.getImage()).into(holder.frame_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DpCreatorShowActivity.class);
                intent.putExtra("images",dpCreator.getImage());
                activity.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView frame_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            frame_image = itemView.findViewById(R.id.frame_image);
        }
    }
}
