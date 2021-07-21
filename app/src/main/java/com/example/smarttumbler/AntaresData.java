package com.example.smarttumbler;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.telkom.iot.AntaresHTTPAPI;
import id.co.telkom.iot.AntaresResponse;

public class AntaresData implements AntaresHTTPAPI.OnResponseListener {


    private AntaresHTTPAPI antaresAPIHTTP;
    private String TAG = "ANTARES-API";
    private String dataDevice;
    private static final int JOB_ID = 0;
    private final int volumeBotol = 750;
    private final String KEY = "96298b1c6436e39f:7bb2ceaf29a16e52";
    private final String APPLICATION_NAME = "Tumblerion";
    private final String DEVICE_NAME ="SensorBerat";

    private double berat = 0.0;

    public AntaresData(){
        antaresAPIHTTP = new AntaresHTTPAPI();
        antaresAPIHTTP.addListener(this);
        antaresAPIHTTP.getLatestDataofDevice(KEY,APPLICATION_NAME,DEVICE_NAME);
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
                berat = test;
                String weight = String.valueOf(test);
                Log.d(TAG,weight);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public double getBerat() {
        return berat;
    }
}
