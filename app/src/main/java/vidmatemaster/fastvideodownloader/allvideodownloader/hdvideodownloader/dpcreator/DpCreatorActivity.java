package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.dpcreator;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.DpCreatorAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.creator_model.DpCreator;

import java.util.ArrayList;

public class DpCreatorActivity extends BaseActivity {

    RecyclerView creator_recycle;
    TextView creator_header;
    ImageView back;
    DpCreatorActivity activity;
    DpCreatorAdapter dpCreatorAdapter;
    ArrayList<DpCreator> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_creator);

        activity = this;

        list.add(new DpCreator(R.drawable.frame_1));
        list.add(new DpCreator(R.drawable.frame_2));
        list.add(new DpCreator(R.drawable.frame_3));
        list.add(new DpCreator(R.drawable.frame_4));
        list.add(new DpCreator(R.drawable.frame_5));
        list.add(new DpCreator(R.drawable.frame_6));
        list.add(new DpCreator(R.drawable.frame_13));
        list.add(new DpCreator(R.drawable.frame_14));
        list.add(new DpCreator(R.drawable.frame_15));
        list.add(new DpCreator(R.drawable.frame_16));
        list.add(new DpCreator(R.drawable.frame_17));
        list.add(new DpCreator(R.drawable.frame_18));
        list.add(new DpCreator(R.drawable.frame_19));
        list.add(new DpCreator(R.drawable.frame_20));
        list.add(new DpCreator(R.drawable.frame_21));
        list.add(new DpCreator(R.drawable.frame_22));
        list.add(new DpCreator(R.drawable.frame_23));
        list.add(new DpCreator(R.drawable.frame_24));
        list.add(new DpCreator(R.drawable.frame_25));
        list.add(new DpCreator(R.drawable.frame_26));
        list.add(new DpCreator(R.drawable.frame_27));
        list.add(new DpCreator(R.drawable.frame_28));
        list.add(new DpCreator(R.drawable.frame_29));
        list.add(new DpCreator(R.drawable.frame_30));
        list.add(new DpCreator(R.drawable.frame_31));
        list.add(new DpCreator(R.drawable.frame_32));
        list.add(new DpCreator(R.drawable.frame_33));
        list.add(new DpCreator(R.drawable.frame_34));
        list.add(new DpCreator(R.drawable.frame_35));
        list.add(new DpCreator(R.drawable.frame_36));
        list.add(new DpCreator(R.drawable.frame_37));
        list.add(new DpCreator(R.drawable.frame_38));

        creator_recycle = findViewById(R.id.creator_recycle);
        creator_header = findViewById(R.id.creator_header);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        creator_recycle.setLayoutManager(new GridLayoutManager(activity,4));
        dpCreatorAdapter = new DpCreatorAdapter(activity,list);
        creator_recycle.setAdapter(dpCreatorAdapter);
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}