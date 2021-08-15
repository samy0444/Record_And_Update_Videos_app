package com.recorder.awkscreenrecorder.Utills;

import android.os.Environment;

import com.recorder.awkscreenrecorder.Model.ModelMyGallery;

import java.util.ArrayList;

public class Constance {
    public static boolean AllowToOpenAdvertise = false;
    public static boolean isFirstTimeOpen = true;
    public static boolean notificationrecordclick = false;
    public static boolean notificationscreenshotclick = false;
    public static boolean isfloatingswitchEnabled = false;

    //public static String adType = "facebook";
    public static String adType = "Ad Mob";

    public static ArrayList<ModelMyGallery> modelMyGalleries;

    public static String PolicyUrl = "https://www.google.com/";

    public static String shareapp_url = "Check out the App at: https://play.google.com/store/apps/details?id=";

    public static String rateapp = "http://play.google.com/store/apps/details?id=";

    public static String aboutUs = "";
    // public static File FileDirectory = Environment.getExternalStoragePublicDirectory("Screen Recorder" + "/");
    public static String PathFileDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Screen Recorder";
    public static String pathScreenShotDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath()+"/DemoScreenShots/";
    //public static String pathcamerafiles = Environment.getExternalStoragePublicDirectory(Environment.e)

}
