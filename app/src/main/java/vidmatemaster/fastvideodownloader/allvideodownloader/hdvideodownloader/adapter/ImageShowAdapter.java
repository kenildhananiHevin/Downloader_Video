package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.OpenGallery;

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder> {
    OpenGallery activity;
    List<String> list;

    public ImageShowAdapter(OpenGallery activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ImageShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.image_show, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ImageShowAdapter.ViewHolder holder, int position) {
        Glide.with(activity).load(list.get(position)).override(200, 200).placeholder(R.color.white).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("image", list.get(position));
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
