package com.example.smarttumbler.health;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttumbler.AntaresData;
import com.example.smarttumbler.R;
import com.example.smarttumbler.botol.Botol;
import com.example.smarttumbler.botol.BotolViewModel;
import com.example.smarttumbler.notifikasi.AlertReceiver;
import com.example.smarttumbler.notifikasi.NotifikasiAir;
import com.example.smarttumbler.profile.Profile;
import com.example.smarttumbler.profile.ProfileViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class HealthFragment extends Fragment implements AntaresHTTPAPI.OnResponseListener{

    private TextView txtData, airMinum,sisaAir;
    private String TAG = "ANTARES-API";
    private AntaresHTTPAPI antaresAPIHTTP;
    private String dataDevice;
    private ImageButton btnRefresh,btnNotify;
    private ProfileViewModel profileViewModel;
    private BotolViewModel botolViewModel;
    private int kebutuhanAir;
    private Date currentDate;
    private int idSekarang;

    private JobScheduler scheduler;

    private double sisaAirYangHarusDiminum,airYangSudahDiminum;

    private static final int JOB_ID = 0;
    private final int volumeBotol = 750;
    private final String KEY = "96298b1c6436e39f:7bb2ceaf29a16e52";
    private final String APPLICATION_NAME = "Tumblerion";
    private final String DEVICE_NAME ="SensorBerat";
    boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ProfileViewModel.class);
        profileViewModel.getAllProfile().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                for(Profile prof: profiles){
                    kebutuhanAir = prof.getKebutuhanAir();
                }
            }
        });
        // Inflate the layout for this fragment
        currentDate = Calendar.getInstance().getTime();
        botolViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BotolViewModel.class);
        airYangSudahDiminum = 0.0;
        sisaAirYangHarusDiminum = kebutuhanAir;
        botolViewModel.getAllBotol().observe(getViewLifecycleOwner(), new Observer<List<Botol>>() {
            @Override
            public void onChanged(List<Botol> botols) {
                for (Botol botol: botols){
                    String hari;
                    SimpleDateFormat tanggalSekarang = new SimpleDateFormat("dd/MM/yyyy");
                    hari = tanggalSekarang.format(botol.getTanggal());
                    if(tanggalSekarang.format(currentDate).equals(hari)){
                        flag = true;
                        Log.d(TAG, "Air yang sudah diminum "+String.valueOf(botol.getAirYangSudahDiminum()));
                        Log.d(TAG, "Sisa air "+String.valueOf(botol.getSisaAir()));
                        Log.d(TAG, "Hari "+ hari);
                        Log.d("botolViewModel","true");
                        idSekarang = botol.getId();
                        break;
                    }else {
                        flag = false;
                    }
                }
            }
        });
        if(flag=false){
            Botol bootle = new Botol(currentDate,airYangSudahDiminum,sisaAirYangHarusDiminum);
            botolViewModel.insert(bootle);
            Log.d(TAG, "onChanged: IN");
        }


        //Insert database currentdate

        View root = inflater.inflate(R.layout.fragment_health, container, false);


        btnRefresh = root.findViewById(R.id.btnRefresh);
        btnNotify = root.findViewById(R.id.btnNotify);
        txtData = root.findViewById(R.id.keteranganAir3);
        airMinum = root.findViewById(R.id.keteranganAir2);
        sisaAir = root.findViewById(R.id.keteranganAir1);

        // --- Inisialisasi API Antares --- //
        //antaresAPIHTTP = AntaresHTTPAPI.getInstance();
        antaresAPIHTTP = new AntaresHTTPAPI();
        antaresAPIHTTP.addListener(this);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Example runnable = new Example(10);
//                new Thread(runnable).start();
                antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);
//                Double hasil = new AntaresData().getBerat();
//                Log.d(TAG, "Hasil : " + hasil);
            }
        });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Toast.makeText(getActivity(),"Notification Will Be Send",Toast.LENGTH_SHORT).show();
            }
        });
    return root;
    }


    @Override
    public void onResponse(AntaresResponse antaresResponse) {
        Log.d(TAG,Integer.toString(antaresResponse.getRequestCode()));
        if(antaresResponse.getRequestCode()==0){
            try {
                JSONObject body = new JSONObject(antaresResponse.getBody());
                dataDevice = body.getJSONObject("m2m:cin").getString("con");
                JSONObject obj = new JSONObject(dataDevice);
                Double test =  Math.floor(obj.getDouble("weight")*1000);
                String weight = String.valueOf(test);
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        botolViewModel.getAllBotol().observe(getViewLifecycleOwner(), new Observer<List<Botol>>() {
                            @Override
                            public void onChanged(List<Botol> botols) {
                                for(Botol botol:botols){
                                    airYangSudahDiminum = botol.getAirYangSudahDiminum();
                                    sisaAirYangHarusDiminum = botol.getAirYangSudahDiminum();
                                }
                            }
                        });
                        txtData.setText(weight+" ML");
                        airYangSudahDiminum = volumeBotol - test;

                        sisaAirYangHarusDiminum =  Math.floor(kebutuhanAir) - Math.floor(airYangSudahDiminum);
                        airMinum.setText(String.valueOf(airYangSudahDiminum) + "ML");
                        sisaAir.setText(String.valueOf(sisaAirYangHarusDiminum) + "ML");

                        Botol bootle = new Botol(currentDate,airYangSudahDiminum,sisaAirYangHarusDiminum);
                        bootle.setId(idSekarang);
                        botolViewModel.update(bootle);

                    }
                });
                Log.d(TAG,weight);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Example implements Runnable{
        int seconds;
        Example(int seconds){
            this.seconds = seconds;
        };

        @Override
        public void run() {
            for (int i= 0;i<seconds;i++){
                antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startAlarm() {
        int seconds = 5;
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, seconds, pendingIntent);
    }

    private void scheduleJobRemainingWater(){
        scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getActivity().getPackageName(),
                NotifikasiAir.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobInfo myJobInfo = builder.build();
        scheduler.schedule(myJobInfo);
        Log.d("Test","Schedule");
    }

    @Override
    public void onStart() {
        super.onStart();
        antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);

    }
}