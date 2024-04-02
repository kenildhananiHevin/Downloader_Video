package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.howtodownload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

public class WhatsappHowToDownloadFragment extends Fragment {
    ImageView wp_how_to_image, wp_how_to;
    TextView next, got_it;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whatsapp_how_to_download, container, false);

        wp_how_to = view.findViewById(R.id.wp_how_to);
        wp_how_to_image = view.findViewById(R.id.wp_how_to_image);
        next = view.findViewById(R.id.next);
        got_it = view.findViewById(R.id.got_it);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wp_how_to_image.setVisibility(View.GONE);
                wp_how_to.setVisibility(View.VISIBLE);
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