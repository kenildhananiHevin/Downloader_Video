package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;

public class DownloadedMediaInfoModel implements Parcelable {
    private File mediaFile;
    private String mediaName;
    private String mediaSize;
    private String mediaDuration;
    private String mediaPath;
    String imagePath;
    String pack;

    public DownloadedMediaInfoModel() {
    }

    protected DownloadedMediaInfoModel(Parcel in) {
        mediaFile = (File) in.readSerializable();
        mediaName = in.readString();
        mediaSize = in.readString();
        mediaDuration = in.readString();
        mediaPath = in.readString();
        imagePath = in.readString();
        pack = in.readString();
    }

    public static final Creator<DownloadedMediaInfoModel> CREATOR = new Creator<DownloadedMediaInfoModel>() {
        @Override
        public DownloadedMediaInfoModel createFromParcel(Parcel in) {
            return new DownloadedMediaInfoModel(in);
        }

        @Override
        public DownloadedMediaInfoModel[] newArray(int size) {
            return new DownloadedMediaInfoModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mediaFile);
        dest.writeString(mediaName);
        dest.writeString(mediaSize);
        dest.writeString(mediaDuration);
        dest.writeString(mediaPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setMediaFile(File mediaFile) {
        this.mediaFile = mediaFile;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public void setMediaSize(String mediaSize) {
        this.mediaSize = mediaSize;
    }

    public void setMediaDuration(String mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public File getMediaFile() {
        return mediaFile;
    }

    public String getMediaName() {
        return mediaName;
    }

    public String getMediaSize() {
        return mediaSize;
    }

    public String getMediaDuration() {
        return mediaDuration;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }
}

