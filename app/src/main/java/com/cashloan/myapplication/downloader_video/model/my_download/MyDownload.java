package com.cashloan.myapplication.downloader_video.model.my_download;

import java.io.File;
import java.util.ArrayList;

public class MyDownload {

    String name;
    ArrayList<File> files;

    public MyDownload(String name, ArrayList<File> files) {
        this.name = name;
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
