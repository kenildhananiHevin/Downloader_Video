package com.cashloan.myapplication.downloader_video.other;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPre {
    public static String COOKIES = "Cookies";
    public static String CSRF = "csrf";
    private static final String USER_PREFS = "com.mediadownloader.instafbdownload.videosaver";
    public static final String ISINSTALOGIN = "IsInstaLogin";
    public static final String SESSIONID = "session_id";
    public static final String USERID = "user_id";
    public static final String WHATSUPKEY = "whatsapp";
    public static final String ISFBLOGIN = "isFbLogin";
    public static final String FBCOOKIES = "fbCookies";
    public static final String FBKEY = "fbKey";
    public static final String CHECKPRMISSSIONKEY = "check_permission";
    public static final String SELECTEDVIDEOPOSITION = "PlayedVidePos";
    public static final String LASTPLAYEDPOSITION = "LastPlayedPosition";
    public static final String FIRSTTIMEAPPSTARTINTROSCREENS = "IsUserFirsttimeIntro";
    public static final String SELECTEDLANGUAGE = "selectedlanguage";
    public static final String FIRSTTIMELANGUAGESCREENS = "firsttimelanguagescreen";
    public static String PREFERENCE = "AllInOneDownloader";
    private static SharedPre instance;
    private Context ctx;
    private SharedPreferences sharedPreferences;



    private static SharedPreferences getPrefEdit(Context context) {
        return context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }

    public static void sharedPutString(Context context, String key, String value) {
        getPrefEdit(context).edit().putString(key, value).apply();
    }

    public static String sharedGetString(Context context, String key) {
        return getPrefEdit(context).getString(key, "");
    }

    public static void sharedPutInt(Context context, String key, int value) {
        getPrefEdit(context).edit().putInt(key, value).apply();
    }

    public static int sharedGetInt(Context context, String key, int defaultValue) {
        return getPrefEdit(context).getInt(key, defaultValue);
    }

    public static void sharedPutBoolean(Context context, String key, boolean value) {
        getPrefEdit(context).edit().putBoolean(key, value).apply();
    }

    public static boolean sharedGetBoolean(Context context, String key) {
        return getPrefEdit(context).getBoolean(key, false);
    }

    public static void sharedPutLong(Context context, String key, long value) {
        getPrefEdit(context).edit().putLong(key, value).apply();
    }

    public static long sharedGetLong(Context context, String key, int defaultValue) {
        return getPrefEdit(context).getLong(key, 0);
    }

    public SharedPre(Context context) {
        this.ctx = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static SharedPre getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPre(context);
        }
        return instance;
    }
}
