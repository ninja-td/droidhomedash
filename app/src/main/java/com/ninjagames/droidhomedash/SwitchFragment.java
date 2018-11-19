package com.ninjagames.droidhomedash;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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


public class SwitchFragment extends Fragment {
    private static final String IFTTT_URL = "https://maker.ifttt.com/trigger/%s/with/key/%s";
    private String apiKey = "k4XHOr72W2cuYaa2DEr4N4PcRHDOvahuLr7T16SmuYP";
    private String eventName = "officelight";
    private boolean isOn = false;


    TextView settingsButton;
    TextView toggleButton;


    private double latitude = 0;
    private double longitude = 0;


    UrlFetchHelper.Callback iftttCallback = new UrlFetchHelper.Callback() {

        @Override
        public void onComplete(String result) {

        }
    };

    public SwitchFragment() {
    }

    private void readSettings() {
        eventName = PreferenceHelper.getString(getActivity(), "switch_name");
        Log.i("SwitchFragment", eventName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_switch, container, false);


        settingsButton = rootView.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SwitchFragment", "opening settings");
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        toggleButton = rootView.findViewById(R.id.toggle_button);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readSettings();
                isOn = !isOn;
                if (isOn) {
                    toggleButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.switch_on_icon));
                } else {
                    toggleButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.switch_off_icon));
                }
                String eventString = eventName + (isOn ? "_on" : "_off");
                new UrlFetchHelper(iftttCallback).execute(String.format(IFTTT_URL, eventString, apiKey));
            }
        });

        return rootView;
    }
}