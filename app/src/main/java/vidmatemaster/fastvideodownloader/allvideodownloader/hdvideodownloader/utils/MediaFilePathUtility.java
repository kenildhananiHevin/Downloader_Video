package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.ConstMedia;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MediaFilePathUtility {
    public static boolean moveFile(Context context, String str) {
        String str2 = str.split("%")[str.split("%").length - 1];
        Uri fromFile = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + ConstMedia.SAVE_FOLDER_NAME + str2));
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(Uri.parse(str));
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(fromFile, "w");
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    openOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    openOutputStream.flush();
                    openOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(fromFile);
                    context.sendBroadcast(intent);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
