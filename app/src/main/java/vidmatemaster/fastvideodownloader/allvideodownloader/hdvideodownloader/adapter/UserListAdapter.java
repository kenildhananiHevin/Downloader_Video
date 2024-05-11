package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.interfaces.UserListInterface;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_story.TrayModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    Context context;
    public ArrayList<TrayModel> trayModelArrayList;
    public UserListInterface userListInterface;

    public UserListAdapter(Context context, ArrayList<TrayModel> arrayList, UserListInterface userListInterface) {
        this.context = context;
        this.trayModelArrayList = arrayList;
        this.userListInterface = userListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_story_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        holder.realName.setText(trayModelArrayList.get(position).getUser().getFull_name());
        holder.realName.setSelected(true);
        holder.userName.setSelected(true);
        holder.storyPc.setBorderColor(context.getColor(R.color.white));
        holder.storyPc.setBorderWidth(5);
        Glide.with(context).load(trayModelArrayList.get(position).getUser().getProfile_pic_url()).thumbnail(0.2f).into( holder.storyPc);
        holder.RLStoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListAdapter.this.userListInterface.userListClick(position,UserListAdapter.this.trayModelArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        ArrayList<TrayModel> arrayList = this.trayModelArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
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
