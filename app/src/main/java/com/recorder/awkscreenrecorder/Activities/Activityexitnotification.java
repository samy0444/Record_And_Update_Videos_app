package com.recorder.awkscreenrecorder.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.recorder.awkscreenrecorder.Fragment.FragmentHome;

import static com.recorder.awkscreenrecorder.Fragment.FragmentHome.NotificationID;

public class Activityexitnotification extends Activity {

    Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = Activityexitnotification.this;

       // FragmentHome.exitNotificationclick();
        FragmentHome.notificationManager.cancel(NotificationID);
        FragmentHome.notificationManager.cancelAll();
        finish();
       }


}
