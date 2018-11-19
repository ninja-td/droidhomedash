package com.ninjagames.droidhomedash;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ClockFragment extends Fragment {

    TextView hourText;
    TextView minuteText;
    TextView separatorText;
    TextView ampmText;

    Runnable secondUpdate = new Runnable() {
        @Override
        public void run() {
            updateTime();
            secondHandler.postDelayed(secondUpdate, 1000);
        }
    };

    Handler secondHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.clock_layout, container, false);

        hourText = (TextView) rootView.findViewById(R.id.hourtext);
        minuteText = (TextView) rootView.findViewById(R.id.minutetext);
        separatorText = (TextView) rootView.findViewById(R.id.separatortext);
        ampmText = (TextView) rootView.findViewById(R.id.ampmtext);
        //updateTime();
        secondHandler.postDelayed(secondUpdate, 1000);
        return rootView;
    }

    private void updateTime() {
        Date dt = Calendar.getInstance().getTime();
        hourText.setText(String.format("%02d", dt.getHours() % 12));
        minuteText.setText(String.format("%02d", dt.getMinutes()));
        int seconds = dt.getSeconds();
        if (seconds % 2 == 0) {
            separatorText.setText("");
        } else {
            separatorText.setText(":");
        }
        ampmText.setText(dt.getHours() < 12 ? "am" : "pm");
    }
}
