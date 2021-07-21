package com.example.smarttumbler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.smarttumbler.notifikasi.NotifikasiAir;
import com.example.smarttumbler.notifikasi.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    private JobScheduler scheduler;
    private JobScheduler reminder;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","on Start");
        cancelJobs();
        cancelReminder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.profileFragment, R.id.healthFragment, R.id.statisticFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
//        scheduleJobRemainingWater();
    }
    private void scheduleJobRemainingWater(){
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotifikasiAir.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0,serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobInfo myJobInfo = builder.build();
        scheduler.schedule(myJobInfo);
        Log.d("MainActivity","Schedule");
    }

    private void scheduleReminder(){
        reminder = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getPackageName(),
                Reminder.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(1,serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(1800000,300000);
        JobInfo myJobInfo = builder.build();
        Log.d("MainActivity","Schedule Reminder");
        reminder.schedule(myJobInfo);
    }


    public void cancelJobs() {
        if (scheduler != null) {
            scheduler.cancelAll();
            scheduler = null;
            Log.d("MainActivity","Stop Schedule");
        }
    }

    public void cancelReminder(){
        if(reminder != null){
            reminder.cancelAll();
            reminder = null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        scheduleReminder();
        scheduleJobRemainingWater();
    }
}