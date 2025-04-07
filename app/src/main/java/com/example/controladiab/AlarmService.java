package com.example.controladiab;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("AlarmService", "Servicio de alarma iniciado");

        Intent alarmIntent = new Intent(this, MedAlarmActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("medicationName", intent.getStringExtra("medicationName"));
        startActivity(alarmIntent);

        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
