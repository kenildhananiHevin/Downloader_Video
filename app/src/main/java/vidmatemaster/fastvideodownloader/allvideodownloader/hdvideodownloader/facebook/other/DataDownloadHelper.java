package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.facebook.other;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

public class DataDownloadHelper {
    private Context context;
    private FaceBookService mediaDownloaderService;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static boolean isServiceConnected = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mediaDownloaderService = ((FaceBookService.MyLocalinder) iBinder).getService();
            Log.d("TAG", "onServiceConnectedff: ");
            isServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
        }
    };


    public DataDownloadHelper(Context context) {
        this.context = context;
        serviceAndBind();
    }

    public void download(final String str, final String str2, final boolean z) {
        serviceAndBind();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isServiceConnected) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaDownloaderService.download(str, str2, z);
                    }
                });
            }
        }).start();
    }

    private void serviceAndBind() {
        Intent intent = new Intent(context, FaceBookService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        context.startService(intent);
    }

}
