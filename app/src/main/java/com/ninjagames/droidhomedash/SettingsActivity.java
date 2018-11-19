package com.ninjagames.droidhomedash;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.settings_activity);
    }
}
