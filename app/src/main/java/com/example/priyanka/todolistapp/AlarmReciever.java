package com.example.priyanka.todolistapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by PRIYANKA on 11-07-2017.
 */

class AlarmReciever extends BroadcastReceiver{
    static int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder noBuilder=(NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(intent.getStringExtra("TITLE"))
                .setAutoCancel(false)
                .setContentText("Time Over For "+intent.getStringExtra("TITLE"));
        Intent resultIntent=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,i++,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i++,noBuilder.build());
    }
}
