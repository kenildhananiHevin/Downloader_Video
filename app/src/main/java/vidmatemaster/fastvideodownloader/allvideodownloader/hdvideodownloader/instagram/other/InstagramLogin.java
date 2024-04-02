package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.other;

import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.COOKIES;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.CSRF;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.ISINSTALOGIN;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.SESSIONID;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.USERID;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.sharedPutBoolean;
import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.SharedPre.sharedPutString;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.BaseActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;

public class InstagramLogin extends BaseActivity {
    private InstagramLogin thisActivity;
    private String cooci;
    SwipeRefreshLayout refresh;
    WebView  webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_login);
        thisActivity = this;
        refresh = findViewById(R.id.refresh);
        webView = findViewById(R.id.webView);

        MainView();

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainView();
            }
        });

    }

    public void MainView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new WebviewWebclient());
        CookieSyncManager.createInstance(thisActivity);
        CookieManager.getInstance().removeAllCookie();
        webView.loadUrl("https://www.instagram.com/accounts/login/");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                refresh.setRefreshing(newProgress != 100);
            }
        });
    }

    public String findLoginornot(String str, String str2) {
        String cookie = CookieManager.getInstance().getCookie(str);
        if (cookie != null && !cookie.isEmpty()) {
            String[] split = cookie.split(";");
            for (String str3 : split) {
                if (str3.contains(str2)) {
                    return str3.split("=")[1];
                }
            }
        }
        return null;
    }

    class WebviewWebclient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            cooci = CookieManager.getInstance().getCookie(str);
            try {
                String findLoginornot = findLoginornot(str, "sessionid");
                String findLoginornot2 = findLoginornot(str, "csrftoken");
                String findLoginornot3 = findLoginornot(str, "ds_user_id");
                if (findLoginornot != null && findLoginornot2 != null && findLoginornot3 != null) {
                    sharedPutString(thisActivity,COOKIES,cooci);
                    sharedPutString(thisActivity,CSRF,findLoginornot2);
                    sharedPutString(thisActivity, SESSIONID, findLoginornot);
                    sharedPutString(thisActivity, USERID, findLoginornot3);
                    sharedPutBoolean(thisActivity, ISINSTALOGIN, true);
                    webView.destroy();
                    Intent intent = new Intent();
                    intent.putExtra("result", "result");
                    thisActivity.setResult(-1, intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
