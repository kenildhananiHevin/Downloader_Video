package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.instagram.other;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.instagram.DownloadFragment;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.other.CommonClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InstagramDownloadTask extends AsyncTask<String, Integer, String> {
    private final Activity mContext;
    private final String mediaName;

    public InstagramDownloadTask(Activity context, String mediaName) {
        this.mContext = context;
        this.mediaName = mediaName;
    }

    @Override
    protected String doInBackground(String... strArr) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String fileExtension = "";
            String mediaNameLowerCase = mediaName.toLowerCase();
            File directory = new File(CommonClass.mEnstagramVideoPathDirectory.getAbsolutePath());

            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (mediaNameLowerCase.endsWith(".jpg") || mediaNameLowerCase.endsWith(".jpeg")) {
                fileExtension = "jpg";
            } else if (mediaNameLowerCase.endsWith(".png")) {
                fileExtension = "png";
            } else if (mediaNameLowerCase.endsWith(".gif")) {
                fileExtension = "gif";
            } else if (mediaNameLowerCase.endsWith(".bmp")) {
                fileExtension = "jpg";
            } else if (mediaNameLowerCase.endsWith(".webp")) {
                fileExtension = "jpg";
            } else if (mediaNameLowerCase.endsWith(".heic")) {
                fileExtension = "jpg";
            } else if (mediaNameLowerCase.endsWith(".mp4")) {
                fileExtension = "mp4";
            } else if (mediaNameLowerCase.endsWith(".mov")) {
                fileExtension = "mov";
            } else if (mediaNameLowerCase.endsWith(".avi")) {
                fileExtension = "avi";
            } else if (mediaNameLowerCase.endsWith(".mkv")) {
                fileExtension = "mkv";
            } else if (mediaNameLowerCase.endsWith(".flv")) {
                fileExtension = "flv";
            } else if (mediaNameLowerCase.endsWith(".wmv")) {
                fileExtension = "wmv";
            } else if (mediaNameLowerCase.endsWith(".mp3")) {
                fileExtension = "mp3";
            } else if (mediaNameLowerCase.endsWith(".wav")) {
                fileExtension = "wav";
            } else if (mediaNameLowerCase.endsWith(".aac")) {
                fileExtension = "aac";
            } else if (mediaNameLowerCase.endsWith(".ogg")) {
                fileExtension = "ogg";
            } else if (mediaNameLowerCase.endsWith(".flac")) {
                fileExtension = "flac";
            } else {
                fileExtension = "";
            }

//            String fileExtension = mediaName.endsWith(".jpg") ? "jpg" : "mp4";
            fileOutputStream = new FileOutputStream(CommonClass.mEnstagramVideoPathDirectory + "/" + System.currentTimeMillis() + "." + fileExtension);

            HttpURLConnection connection = (HttpURLConnection) new URL(strArr[0]).openConnection();
            inputStream = connection.getInputStream();

            connection.connect();

            if (connection.getResponseCode() != 200) {
                String errorMessage = "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                connection.disconnect();
                return errorMessage;
            }

            int contentLength = connection.getContentLength();
            byte[] buffer = new byte[4096];
            long downloadedSize = 0;

            while (true) {
                int read = inputStream.read(buffer);
                if (read == -1) {
                    fileOutputStream.close();
                    inputStream.close();
                    connection.disconnect();
                    return null;
                }
                if (isCancelled()) {
                    break;
                }
                downloadedSize += read;

                if (contentLength > 0) {
                    publishProgress((int) (100 * downloadedSize / contentLength));
                }
                fileOutputStream.write(buffer, 0, read);
            }
            connection.disconnect();
            return null;
        } catch (Exception e) {
            Log.d("TAG", "doInBackgroundss: "+e.getMessage());
            return e.toString();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showToast(mContext, mContext.getResources().getString(R.string.downloadStarted));
    }

    @Override
    protected void onPostExecute(String str) {
        if (str != null) {
            showToast(mContext, mContext.getResources().getString(R.string.somethingWentWrong) + str);
            return;
        }
        showToast(mContext, mContext.getResources().getString(R.string.downloadCompleted));
        DownloadFragment.mutableLiveData.postValue("");
    }

    private void showToast(Activity activity, String message) {
        activity.runOnUiThread(() -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
    }
}
