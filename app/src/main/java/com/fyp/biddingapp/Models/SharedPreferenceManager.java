package com.fyp.biddingapp.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;

public class SharedPreferenceManager {
    private RequestQueue requestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    @SuppressLint("StaticFieldLeak")
    private static SharedPreferenceManager instance;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_ID = "userId";
    private static final String KEY_NAME = "userName";
    private static final String KEY_EMAIL = "email ";

    private SharedPreferenceManager(Context context) {
        ctx = context;


    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public boolean UserLogin (int userId , String UserName , String Email)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID , userId);
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

    public int getUserID ()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID , 0);
    }

    public String getUserName ()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME , null);
    }



}
