package com.danazone.autosharesms;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PC on 1/17/2018.
 */

public class SessionManager {
    private static final String SHARED_PREFERENCES_NAME = "com.danazone.autosharesms";
    private static final String KEY_SAVE_NAME = "key_save_name";


    private static SessionManager sInstance;

    private SharedPreferences sharedPref;

    public synchronized static SessionManager getInstance() {
        if (sInstance == null) {
            sInstance = new SessionManager();
        }
        return sInstance;
    }

    private SessionManager() {
        // no instance
    }

    public void init(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Set key save email
     *
     * @param token
     */
    public void setKeyName(String token) {
        sharedPref.edit().putString(KEY_SAVE_NAME, token).apply();
    }



    /**
     * get key save email
     *
     * @return
     */
    public String getKeySaveName() {
        return sharedPref.getString(KEY_SAVE_NAME, "");
    }

}

