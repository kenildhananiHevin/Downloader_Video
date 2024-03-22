package com.cashloan.myapplication.downloader_video.model;

import android.net.Uri;
import android.os.Environment;

import androidx.annotation.Keep;

import java.io.File;

@Keep
public class ConstMedia {
    public static final String FOLDER_NAME = "/WhatsApp/";
    public static final String FOLDER_NAME_Whatsapp_and11 = "/Android/media/com.whatsapp/WhatsApp/";
    public static final String FOLDER_NAME_Whatsapp_and11_B = "/Android/media/com.whatsapp.w4b/WhatsApp Business/";
    public static final String FOLDER_NAME_Whatsappbusiness = "/WhatsApp Business/";
    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video_Downloader/WhatsappSaver");
    public static final String SAVE_FOLDER_NAME = "/Download/Video_Downloader/WhatsappSaver/";
    public static final String savedData = "/storage/emulated/0/Download/WhatsappSaver/";

    public static int GALLERY = 1;
    public static Uri contentURI;
    public static String VIDEO_DIRECTORY = "/demonuts";
}
