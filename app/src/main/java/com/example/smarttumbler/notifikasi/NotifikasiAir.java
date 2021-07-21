package com.example.smarttumbler.notifikasi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.smarttumbler.MainActivity;
import com.example.smarttumbler.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class NotifikasiAir extends JobService implements AntaresHTTPAPI.OnResponseListener {


    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Notification manager.
    NotificationManager mNotifyManager;

    private ExecutorService executor;

    private AntaresHTTPAPI antaresAPIHTTP;
    private String TAG = "ANTARES-API";
    private String dataDevice;
    private final String KEY = "96298b1c6436e39f:7bb2ceaf29a16e52";
    private final String APPLICATION_NAME = "Tumblerion";
    private final String DEVICE_NAME ="SensorBerat";

    private Double test = 0.0;
    private String weight ="0";

    private JobParameters job;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            boolean jobFinished = true;
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                Log.d("Thread", "Thread destroyed");
                jobFinished = false;
            }
            boolean finalJobFinished = jobFinished;
                while(true){
                    antaresAPIHTTP = new AntaresHTTPAPI();
                    antaresAPIHTTP.addListener(this);
                    antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);
                    job = jobParameters;
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d("Notifikasi Air","Berat air sekarang : " + test );
                    if(finalJobFinished && test < 150.0){
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
                                .setContentText("Sisa air anda "+weight)
                                .setContentIntent(contentPendingIntent)
                                .setSmallIcon(R.drawable.ic_baseline_notifications)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setAutoCancel(true);

                        mNotifyManager.notify(0, builder.build());
                        jobFinished(job, false);
                    break;
                    }

                }
            });


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        if (executor != null) {
            executor.shutdownNow();
        }
//        Toast.makeText(getApplicationContext(), "Job Failed", Toast.LENGTH_SHORT).show();
        return true;

    }


    @Override
    public void onResponse(AntaresResponse antaresResponse) {
        Log.d(TAG,"test");
        Log.d(TAG,Integer.toString(antaresResponse.getRequestCode()));
        if(antaresResponse.getRequestCode()==0){
            try {
                JSONObject body = new JSONObject(antaresResponse.getBody());
                dataDevice = body.getJSONObject("m2m:cin").getString("con");
                JSONObject obj = new JSONObject(dataDevice);
                test =  Math.floor(obj.getDouble("weight")*1000);
                weight = String.valueOf(test);
                Log.d(TAG,weight +" notif");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
