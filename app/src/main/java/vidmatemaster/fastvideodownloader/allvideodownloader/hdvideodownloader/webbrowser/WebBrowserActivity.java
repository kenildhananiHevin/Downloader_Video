package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.webbrowser;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.adapter.WebBrowserAdapter;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.web_model.WebBrowser;

import java.util.ArrayList;

public class WebBrowserActivity extends BaseActivity {

    TextView web_browser_header;
    ImageView back;
    RecyclerView web_browser_recycle;
    WebBrowserActivity activity;
    WebBrowserAdapter webBrowserAdapter;
    ArrayList<WebBrowser> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        bannerAd();

        activity = this;

        list.add(new WebBrowser(getString(R.string.instagram),"https://www.instagram.com/",R.drawable.web_1));
        list.add(new WebBrowser(getString(R.string.twitter),"https://www.twitter.com/",R.drawable.web_2));
        list.add(new WebBrowser(getString(R.string.linkedin),"https://www.linkedin.com/",R.drawable.web_3));
        list.add(new WebBrowser(getString(R.string.facebook),"https://www.facebook.com/",R.drawable.web_4));
        list.add(new WebBrowser(getString(R.string.sharechat),"https://www.sharechat.com/",R.drawable.web_5));
        list.add(new WebBrowser(getString(R.string.dailymotion),"https://www.dailymotion.com/",R.drawable.web_6));
        list.add(new WebBrowser(getString(R.string.pinterest),"https://www.pinterest.com/",R.drawable.web_7));
        list.add(new WebBrowser(getString(R.string.imdb),"https://www.imdb.com/",R.drawable.web_8));
        list.add(new WebBrowser(getString(R.string.bittube),"https://www.bittube.com/",R.drawable.web_9));
        list.add(new WebBrowser(getString(R.string.tumblr),"https://www.tumblr.com/",R.drawable.web_10));
        list.add(new WebBrowser(getString(R.string.twitch),"https://www.twitch.com/",R.drawable.web_11));
        list.add(new WebBrowser(getString(R.string.bitchute),"https://www.bitchute.com/",R.drawable.web_12));
        list.add(new WebBrowser(getString(R.string.metcalfe),"https://www.shopmetcalfes.com/",R.drawable.web_13));
        list.add(new WebBrowser(getString(R.string.fansubs),"https://www.fansubs.com/",R.drawable.web_14));
        list.add(new WebBrowser(getString(R.string.flickr),"https://www.flickr.com/",R.drawable.web_15));
        list.add(new WebBrowser(getString(R.string.ifunny),"https://ifunny.co/",R.drawable.web_16));
        list.add(new WebBrowser(getString(R.string.reddit),"https://www.reddit.com/",R.drawable.web_17));
        list.add(new WebBrowser(getString(R.string.rumble),"https://www.rumble.com/",R.drawable.web_18));
        list.add(new WebBrowser(getString(R.string.iziesene),"https://www.izlesene.com/",R.drawable.web_20));
        list.add(new WebBrowser(getString(R.string.onetvru),"https://www.onetv.ca/",R.drawable.web_21));
        list.add(new WebBrowser(getString(R.string.vlipsy),"https://vlipsy.com/",R.drawable.web_22));

        back = findViewById(R.id.back);
        web_browser_header = findViewById(R.id.web_browser_header);
        web_browser_recycle = findViewById(R.id.web_browser_recycle);

        web_browser_header.setSelected(true);

        web_browser_recycle.setLayoutManager(new GridLayoutManager(activity, 4));
        webBrowserAdapter = new WebBrowserAdapter(activity,list);
        web_browser_recycle.setAdapter(webBrowserAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}