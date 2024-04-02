package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.webbrowser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

public class WebBrowserShowActivity extends BaseActivity {
    TextView web_browser_header;
    ImageView back;
    WebBrowserShowActivity activity;
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser_show);

        activity = this;

        String urls = getIntent().getStringExtra("url");

        back = findViewById(R.id.back);
        webView = findViewById(R.id.webView);
        web_browser_header = findViewById(R.id.web_browser_header);
        progressBar = findViewById(R.id.progress);
        web_browser_header.setSelected(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.loadUrl(urls);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}