package com.inonitylab.smartwallet.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 19-Nov-16.
 */
public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session (Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE); //preference name is myapp
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedin){  //used to set after user logs in. as soon as user is logged it, then set it to true and commit it to preferences.
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false); //checks if user is logged in.
    }


}
