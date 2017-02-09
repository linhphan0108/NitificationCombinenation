package com.example.linh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by linh on 09/02/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCombindation.notifications.clear();
        Log.d(MyBroadcastReceiver.class.getName(), "cleared");
    }
}
