package com.cashloan.myapplication.downloader_video.facebook.other;

import android.content.Context;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import java.io.File;
import java.io.IOException;
import java.text.StringCharacterIterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OtherFuntions {
    private Context context;
    private String ua;
    private File facebookDirectory;

    public OtherFuntions(Context context) {
        this.context = context;
        ua = new WebView(context).getSettings().getUserAgentString();
        facebookDirectory = new File(Environment.getExternalStorageDirectory().toString() + "/Download/Facebook/");
    }

    public String getco() {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            try {
                return cookieManager.getCookie("https://m.facebook.com");
            } catch (StringIndexOutOfBoundsException e) {
                return cookieManager.getCookie("https://www.facebook.com");
            }
        } catch (Exception e) {
            return "null";
        }
    }

    public String codeWeb(String str) {
        String str2 = "error";
        OkHttpClient client = new OkHttpClient();
        Log.i("page__", "okhtttp " + str);
        Request.Builder requestBuilder = new Request.Builder()
                .url(str)
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-IN,en-US;q=0.9,en;q=0.8")
                .addHeader("cache-control", "max-age=0")
                .addHeader("origin", "https://www.facebook.com")
                .addHeader("referer", "https://www.facebook.com")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-ch-ua", " Not A;Brand\";v=\"99\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "macOS")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");

        if (getco() != null && !getco().equals("null")) {
            requestBuilder.addHeader("Cookie", getco());
        }

        try {
            str2 = client.newCall(requestBuilder.build()).execute().body().string();
            Log.i("page__", "okhtttp " + str2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str2;
    }

    public String byteCountBin(long j) {
        long abs = (j == Long.MIN_VALUE) ? Long.MAX_VALUE : Math.abs(j);
        if (abs < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return j + " B";
        }

        StringCharacterIterator iterator = new StringCharacterIterator("KMGTPE");
        long j2 = abs;
        int i = 40;
        while (i >= 0 && abs > 1152865209611504844L >> i) {
            j2 >>= 10;
            iterator.next();
            i -= 10;
        }

        return String.format("%.1f %ciB", j2 * Long.signum(j) / 1024.0, iterator.current());
    }

    public String getParentDir() {
        return facebookDirectory.toString() + File.separator;
    }
}
