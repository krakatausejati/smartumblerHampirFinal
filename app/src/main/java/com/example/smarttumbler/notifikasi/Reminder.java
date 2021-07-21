package com.example.smarttumbler.notifikasi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.smarttumbler.MainActivity;
import com.example.smarttumbler.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reminder extends JobService {

    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel_1";

    // Notification manager.
    NotificationManager mNotifyManager;

    private ExecutorService executor;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("Reminder", "onStartJob: Reminder");
        executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            createNotificationChannel();
            PendingIntent contentPendingIntent = PendingIntent.getActivity(
                    getApplicationContext(),
                    0,
                    new Intent(getApplicationContext(), MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext(), PRIMARY_CHANNEL_ID)
                    .setContentTitle("SmartTumbler")
                    .setContentText("Jangan Lupa minum ")
                    .setContentIntent(contentPendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_notifications)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);

            mNotifyManager.notify(0, builder.build());
            jobFinished(jobParameters, false);
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {


        if (executor != null) {
            executor.shutdownNow();
            Log.d("Reminder", "onStopJob: ");
        }
        return true;
    }



    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Notification channels are only a vailable in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "SmartTumbler Notifikasi",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifikasi dari SmartTumbler");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
