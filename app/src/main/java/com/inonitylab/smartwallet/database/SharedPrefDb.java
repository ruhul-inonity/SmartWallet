package com.inonitylab.smartwallet.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 12/27/16.
 */

public class SharedPrefDb {

    private final String SP_NAME = "smart_wallet";
    private final String CATEGORIES = "categories";
    private SharedPreferences preferences;

    public SharedPrefDb(Context context) {
        preferences = context.getSharedPreferences(SP_NAME, 0);
    }

    public void setFirstTimeStatus() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CATEGORIES, 1);
        editor.apply();
    }

    public int getFirstTimeStatus() {
        return preferences.getInt(CATEGORIES,0);
    }
}
