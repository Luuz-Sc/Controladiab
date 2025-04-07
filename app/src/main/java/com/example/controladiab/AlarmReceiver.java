package com.example.controladiab;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;


import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String medicationName = intent.getStringExtra("medicationName");
        String daysSelected = intent.getStringExtra("daysSelected");


        showNotification(context, medicationName, daysSelected);
    }

    private void showNotification(Context context, String medicationName, String daysSelected) {


        Intent notificationIntent = new Intent(context, MedAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "medication_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Recordatorio de Medicamento")
                .setContentText("Es hora de tomar tu medicamento: " + medicationName + "\nDías: " + daysSelected)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "medication_channel";
        CharSequence channelName = "Recordatorio de Medicamento";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);


        builder.setChannelId(channelId);


        notificationManager.notify(0, builder.build());



        notificationManager.notify(0, builder.build());
    }
}
