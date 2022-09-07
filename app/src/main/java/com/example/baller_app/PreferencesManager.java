package com.example.baller_app;

import android.content.Context;
import android.content.SharedPreferences;

 class PreferencesManager {

    private static final String PREF_NAME = "com.example.app.PREF_NAME";
    private static final String KEY_VALUE = "com.example.app.KEY_VALUE";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
//            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
//                    " is not initialized, call initializeInstance(..) method first.");
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

     public void putBoolean(String key,boolean value) {
         mPref.edit()
                 .putBoolean(key, value)
                 .commit();
     }
     public boolean getBoolen(String key) {
         return mPref.getBoolean(key, false);
     }
    public void setValue(String key,long value) {
        mPref.edit()
                .putLong(key, value)
                .commit();
    }

    public long getValue(String key) {
        return mPref.getLong(key, 0);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
