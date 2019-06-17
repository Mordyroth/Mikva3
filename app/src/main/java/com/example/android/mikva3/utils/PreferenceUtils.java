package com.example.android.mikva3.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static PreferenceUtils yourPreference;
    private SharedPreferences sharedPreferences;

    public static PreferenceUtils getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new PreferenceUtils(context);
        }
        return yourPreference;
    }

    private PreferenceUtils(Context context) {
        sharedPreferences = context.getSharedPreferences("Miveh3AppPreference", Context.MODE_PRIVATE);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String get(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void save(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public Integer getInt(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }

    public void removeAll() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }
}

