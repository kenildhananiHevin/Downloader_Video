package com.cashloan.myapplication.downloader_video.model.insta_model;

import android.net.Uri;

import androidx.annotation.Keep;

@Keep
public class StoryModelMedia {
    private String filename;
    String imagePath;
    private String name;
    String pack;
    private String path;
    private Uri uri;
    String videoPath;

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String str) {
        this.videoPath = str;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public String getPack() {
        return this.pack;
    }

    public void setPack(String str) {
        this.pack = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri2) {
        this.uri = uri2;
    }
}
