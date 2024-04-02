package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator.DpCreatorShowActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.creator_model.DpCreator;

public class EditPicAdapter extends RecyclerView.Adapter<EditPicAdapter.ViewHolder> {
    DpCreatorShowActivity activity;
    ArrayList<DpCreator> list;

    public EditPicAdapter(DpCreatorShowActivity activity, ArrayList<DpCreator> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.creator_show_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EditPicAdapter.ViewHolder holder, int position) {
        DpCreator dpCreator = list.get(position);
        Glide.with(activity).load(dpCreator.getImage()).into(holder.frame_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_1);
                } else if (position == 1) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_2);
                } else if (position == 2) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_3);
                } else if (position == 3) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_4);
                } else if (position == 4) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_5);
                } else if (position == 5) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_6);
                } else if (position == 6) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_13);
                } else if (position == 7) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_14);
                } else if (position == 8) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_15);
                } else if (position == 9) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_16);
                } else if (position == 10) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_17);
                } else if (position == 11) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_18);
                } else if (position == 12) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_19);
                } else if (position == 13) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_20);
                } else if (position == 14) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_21);
                } else if (position == 15) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_22);
                } else if (position == 16) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_23);
                } else if (position == 17) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_24);
                } else if (position == 18) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_25);
                } else if (position == 19) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_26);
                } else if (position == 20) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_27);
                } else if (position == 21) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_28);
                } else if (position == 22) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_29);
                } else if (position == 23) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_30);
                } else if (position == 24) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_31);
                } else if (position == 25) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_32);
                } else if (position == 26) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_33);
                } else if (position == 27) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_34);
                } else if (position == 28) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_35);
                } else if (position == 29) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_36);
                } else if (position == 30) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_37);
                } else if (position == 31) {
                    DpCreatorShowActivity.frame_select.setImageResource(R.drawable.frame_38);
                }
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
