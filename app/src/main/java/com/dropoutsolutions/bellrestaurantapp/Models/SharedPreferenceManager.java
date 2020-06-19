package com.dropoutsolutions.bellrestaurantapp.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_ID = "userid";
    private static final String KEY_NAME = "username";
    private static final String KEY_EMAIL = "useremail ";

    private SharedPreferenceManager(Context context) {
        ctx = context;


    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public boolean UserLogin (int id , String UserName , String Email)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID , id);
        editor.putString(KEY_NAME , UserName);
        editor.putString(KEY_EMAIL , Email);
        editor.apply();
        return true ;
    }

    public boolean loggedin ()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_NAME , null)!= null)
        {
            return  true ;
        }
        return false ;
    }


    public  boolean logout()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        return true;

    }



}
