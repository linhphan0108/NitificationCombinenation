package com.example.linh.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * a sample app that demonstrate how to combine notifications
 * notes: this feature doesn't work on Xiaomi's devices
 */
public class NotificationCombindation extends AppCompatActivity implements View.OnClickListener {

    Button mButton;

    static List<String> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_combindation);

        mButton = (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String message = "notification " + notifications.size();
        sendNotification(this, message);
    }

    private void sendNotification(Context cxt, String messageBody) {

        //onDismiss Intent
        Intent intent = new Intent(cxt, MyBroadcastReceiver.class);
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(cxt.getApplicationContext(), 0, intent, 0);

        //OnClick Listener
        Intent launchIntent = new Intent(cxt, NotificationCombindation.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(cxt, 0, launchIntent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(cxt)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Title - Notification");
        inboxStyle.setSummaryText("You have "+ notifications.size()+" Notifications.");
        // Moves events into the expanded layout
        notifications.add(messageBody);
        for (int i=0; i < notifications.size(); i++) {
            inboxStyle.addLine(notifications.get(i));
        }
        // Moves the expanded layout object into the notification object.
        notificationBuilder.setStyle(inboxStyle);

//        NotificationManager notificationManager =
//                (NotificationManager) cxt.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationBuilder.setDeleteIntent(broadcastIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
