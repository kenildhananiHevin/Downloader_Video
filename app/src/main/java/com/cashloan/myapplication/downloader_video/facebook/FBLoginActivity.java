package com.cashloan.myapplication.downloader_video.facebook;

import com.cashloan.myapplication.downloader_video.BaseActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.airbnb.lottie.utils.Utils;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.other.CommonClass;
import com.cashloan.myapplication.downloader_video.other.SharedPre;

import java.io.PrintStream;

public class FBLoginActivity extends BaseActivity {
    FBLoginActivity activity;
    public String cookies;
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);

        swipeRefreshLayout = findViewById(R.id.refresh);
        webView = findViewById(R.id.webView);
        activity = this;
        lambda$onCreate$0$FBLoginActivity();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                lambda$onCreate$0$FBLoginActivity();
            }
        });
    }

    private void lambda$onCreate$0$FBLoginActivity() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        CookieSyncManager.createInstance(activity);
        CookieManager.getInstance().removeAllCookie();
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(activity, "Android");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            settings.setMixedContentMode(2);
        }
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, (Paint) null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, (Paint) null);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl("https://www.facebook.com/");

    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            if (Build.VERSION.SDK_INT < 21) {
                return true;
            }
            webView.loadUrl(webResourceRequest.getUrl().toString());
            String unused = cookies = CookieManager.getInstance().getCookie(webResourceRequest.getUrl().toString());
            if (CommonClass.isNullOrEmpty(cookies) || !cookies.contains("c_user")) {
                return true;
            }
            SharedPre.getInstance(activity).sharedPutString(activity,SharedPre.FBCOOKIES,cookies);
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            String unused = FBLoginActivity.this.cookies = CookieManager.getInstance().getCookie(str);
            if (CommonClass.isNullOrEmpty(cookies) || !cookies.contains("c_user")) {
                return true;
            }
            SharedPre.getInstance(activity).sharedPutString(activity,SharedPre.FBCOOKIES,cookies);
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            FBLoginActivity.this.cookies = CookieManager.getInstance().getCookie(str);
            webView.loadUrl("javascript:Android.resultOnFinish();");
            webView.loadUrl("javascript:var el = document.querySelectorAll('input[name=fb_dtsg]');Android.resultOnFinish(el[0].value);");
        }
    }

    @JavascriptInterface
    public void resultOnFinish(String str) {
        if (str.length() >= 15) {
            try {
                if (!CommonClass.isNullOrEmpty(cookies) && cookies.contains("c_user")) {
                    SharedPre.getInstance(activity).sharedPutString(activity,SharedPre.FBKEY, str);
                    SharedPre.getInstance(activity).sharedPutBoolean(activity,SharedPre.ISFBLOGIN, true);
                    PrintStream printStream = System.out;
                    printStream.println("Key - " + str);
                    Intent intent = new Intent();
                    intent.putExtra("result", "result");
                    setResult(-1, intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}