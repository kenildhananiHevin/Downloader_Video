package com.cashloan.myapplication.downloader_video.facebook.other;

import static com.cashloan.myapplication.downloader_video.fragment.facebook.FaceBookDownloadFragment.mutableLiveDataFb;
import static com.cashloan.myapplication.downloader_video.other.CommonClass.showToast;
import static com.cashloan.myapplication.downloader_video.other.SharedPre.SELECTEDLANGUAGE;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.LocaleList;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.other.SharedPre;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.Func;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FaceBookService extends Service {
    private OtherFuntions faceBookFunctions;
    private IBinder myIbinder = new MyLocalinder();
    private int id = 1;
    private int i = 0;
    private static Fetch fetch;
    private static List<Integer> list;
    public static File mFaceBookVideoPathDirectory = new File(Environment.getExternalStorageDirectory() + "/Download/Video_Downloader/FaceBookSavedVideo");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        list = new ArrayList<>();
        return myIbinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopSelf();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initFetch();
        try {
            addListener();
        } catch (Exception unused) {
            return START_NOT_STICKY;
        }
        return START_NOT_STICKY;
    }

    private void addListener() {
        fetch.addListener(new FetchListener() {
            @Override
            public void onCancelled(@NonNull Download download) {}

            @Override
            public void onDeleted(@NonNull Download download) {}

            @Override
            public void onPaused(@NonNull Download download) {}

            @Override
            public void onRemoved(@NonNull Download download) {}

            @Override
            public void onResumed(@NonNull Download download) {}

            @Override
            public void onProgress(@NonNull Download download, long l, long l1) {}

            @Override
            public void onQueued(@NonNull Download download) {
                if (!list.contains(download.getId())) {
                    list.add(download.getId());
                    i = 0;
                }
            }

            @Override
            public void onCompleted(@NonNull Download download) {
                if (i == 0) {
                    i++;
                }
            }

            @Override
            public void onError(@NonNull Download download) {}
        });
    }

    public void initFetch() {
        faceBookFunctions = new OtherFuntions(this);
        if (fetch == null) {
            try {
                fetch = new Fetch.Builder(this, "Main")
                        .setDownloadConcurrentLimit(4)
                        .enableLogging(true)
                        .build();
            } catch (Exception unused) {}
        }
    }

    public void download(String str, String str2, boolean z) {
        Log.d("TAG", "downloadsss: "+str);
        Log.d("TAG", "downloadsss1: "+str2);
        Context updatedContext = changeLanguage(SharedPre.sharedGetString(getApplicationContext(), SELECTEDLANGUAGE));
        String replaceAll = str2.replaceAll("/", "").replaceAll("\\|", "").replaceAll("\\\\", "");
        initFetch();
        try {
            addListener();
        } catch (Exception unused) {}

        if (!mFaceBookVideoPathDirectory.exists()) {
            mFaceBookVideoPathDirectory.mkdirs();
        }
        Request request = new Request(str, mFaceBookVideoPathDirectory + "/" + replaceAll);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        fetch.enqueue(request, new Func<Download>() {
            @Override
            public void call(@NonNull Download download) {
                showToast(getApplicationContext(), updatedContext.getResources().getString(R.string.downloadStarted));
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast(getApplicationContext(), updatedContext.getString(R.string.downloadCompleted));
                        mutableLiveDataFb.postValue("");
                    }
                },5000);
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error error) {
                showToast(getApplicationContext(), updatedContext.getResources().getString(R.string.somethingWentWrong));
            }
        });
    }

    private Context changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new LocaleList(locale));
        } else {
            configuration.setLocale(locale);
        }
        configuration.setLayoutDirection(locale);
        return createConfigurationContext(configuration);
    }

    public class MyLocalinder extends Binder {
        public FaceBookService getService() {
            list = new ArrayList<>();
            return FaceBookService.this;
        }
    }
}
