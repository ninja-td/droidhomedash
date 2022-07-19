package com.ninjagames.droidhomedash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;


public class WeatherFragment extends Fragment {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=b77506397058946d5cece6d611705ba6";

    TextView tempText;
    TextView minMaxText;
    TextView weatherText;


    private double latitude = 37.3688;  // For Sunnyvale
    private double longitude = -122.0363;


    UrlFetchHelper.Callback weatherUrlCallback = new UrlFetchHelper.Callback() {

        @Override
        public void onComplete(String result) {
            updateWeather(result);

        }
    };
    Runnable weatherUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            new UrlFetchHelper(weatherUrlCallback).execute(String.format(WEATHER_URL, latitude, longitude));
            weatherUpdateHandler.postDelayed(weatherUpdateRunnable, 60000);
        }
    };
    Handler weatherUpdateHandler = new Handler(Looper.getMainLooper());



    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        updateLocation();
        weatherText = rootView.findViewById(R.id.weather_text);
        tempText = rootView.findViewById(R.id.temp_text);
        minMaxText = rootView.findViewById(R.id.minmax_text);
        weatherUpdateRunnable.run();
        return rootView;
    }

    private void requestLocationPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
               // ActivityCompat.requestPermissions(getActivity(),
               //         new String[]{Manifest.permission.READ_CONTACTS},
               //         MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }

    private void updateLocation() {

        /*
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        */

        try {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Log.i("Dashboard", String.valueOf(longitude));

        } catch (SecurityException securityException) {
            Log.i("Dashboard", "Security Exception accessing location");

        }

    }

    private void updateWeather(String weather) {
        try {
            JSONObject weatherObj = new JSONObject(weather);
            JSONObject mainStats = weatherObj.getJSONObject("main");
            String mainText = weatherObj.getJSONArray("weather").getJSONObject(0).getString("main");
            int mainTemp = mainStats.getInt("temp");
            int maxTemp = mainStats.getInt("temp_max");
            int minTemp = mainStats.getInt("temp_min");
            tempText.setText(String.format("%.1f", KelvinToCelcius(mainTemp)));
            weatherText.setText(mainText);
            minMaxText.setText(String.format("%.1f \u00B0C/%.1f \u00B0C", KelvinToCelcius(minTemp), KelvinToCelcius(maxTemp)));
        } catch (Exception e) {
            Log.e("WeatherFragment", e.toString());
        }

    }

    private double KelvinToCelcius(double inKelvin) {
        return inKelvin - 273.16;
    }
}
