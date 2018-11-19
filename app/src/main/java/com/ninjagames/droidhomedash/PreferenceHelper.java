package com.ninjagames.droidhomedash;

import android.content.Context;

public class PreferenceHelper {

    public static String getString(Context context, String key) {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE).getString(key, "");
    }

    public static void setString(Context context, String key, String value) {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

}
