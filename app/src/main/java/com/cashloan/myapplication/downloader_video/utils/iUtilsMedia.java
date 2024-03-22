package com.cashloan.myapplication.downloader_video.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.cashloan.myapplication.downloader_video.model.ConstMedia;

import java.io.File;


public class iUtilsMedia {
    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void scanFile(Activity activity, String str) {
        MediaScannerConnection.scanFile(activity, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() + ConstMedia.SAVE_FOLDER_NAME + str}, new String[]{"*/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
            }

            public void onScanCompleted(String str, Uri uri) {
                
            }
        });
    }

    public static void checkFolder() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + ConstMedia.SAVE_FOLDER_NAME);
        boolean exists = file.exists();
        if (!exists) {
            exists = file.mkdir();
        }
        if (exists) {
            
        }
    }
}
