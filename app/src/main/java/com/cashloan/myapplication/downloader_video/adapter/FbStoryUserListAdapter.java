package com.cashloan.myapplication.downloader_video.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.interfaces.UserListInterface;
import com.cashloan.myapplication.downloader_video.model.facebook_model.NodeModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FbStoryUserListAdapter extends RecyclerView.Adapter<FbStoryUserListAdapter.ViewHolder> {
    FragmentActivity requireActivity;
    ArrayList<NodeModel> trayModelArrayList;
    UserListInterface userListInterface;

    public FbStoryUserListAdapter(FragmentActivity requireActivity, ArrayList<NodeModel> trayModelArrayList, UserListInterface userListInterface) {
        this.requireActivity = requireActivity;
        this.trayModelArrayList = trayModelArrayList;
        this.userListInterface = userListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(requireActivity).inflate(R.layout.item_story_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FbStoryUserListAdapter.ViewHolder holder, int position) {
        NodeModel nodeModel = trayModelArrayList.get(position);
        holder.realName.setText(nodeModel.getNodeDataModel().getStory_bucket_owner().get("name").getAsString());
        Glide.with(requireActivity).load(nodeModel.getNodeDataModel().getOwner().getAsJsonObject("profile_picture").get("uri").getAsString()).thumbnail(0.2f).into((ImageView) holder.storyPc);
        holder.realName.setSelected(true);
        holder.userName.setSelected(true);
        holder.RLStoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListInterface.fbUserListClick(position, trayModelArrayList.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        ArrayList<NodeModel> arrayList = this.trayModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return trayModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout RLStoryLayout;
        TextView realName;
        CircleImageView storyPc;
        TextView userName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RLStoryLayout = itemView.findViewById(R.id.RLStoryLayout);
            realName = itemView.findViewById(R.id.real_name);
            storyPc = itemView.findViewById(R.id.story_pc);
            userName = itemView.findViewById(R.id.user_name);
        }
    }
}
