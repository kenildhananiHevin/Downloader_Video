package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.howtodownload;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

public class FaceBookHowToDownloadFragment extends Fragment {

    ImageView fb_how_to_image, fb_how_to;
    TextView next,got_it;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_face_book_download2, container, false);

        fb_how_to = view.findViewById(R.id.fb_how_to);
        fb_how_to_image = view.findViewById(R.id.fb_how_to_image);
        next = view.findViewById(R.id.next);
        got_it = view.findViewById(R.id.got_it);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_how_to_image.setVisibility(View.GONE);
                fb_how_to.setVisibility(View.VISIBLE);
                got_it.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
            }
        });

        got_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        return view;
    }
}