package com.fyp.biddingapp.Models

import com.android.volley.RequestQueue
import android.content.SharedPreferences
import com.fyp.biddingapp.Models.SharedPreferenceManager
import android.annotation.SuppressLint
import android.content.Context
import kotlin.jvm.Synchronized

class SharedPreferenceManager private constructor(@field:SuppressLint("StaticFieldLeak") private var ctx: Context) {
    private val requestQueue: RequestQueue? = null
    fun UserLogin(userId: Int, UserName: String?, Email: String?): Boolean {
        val sharedPreferences: SharedPreferences = Companion.ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_ID, userId)
        editor.putString(KEY_NAME, UserName)
        editor.putString(KEY_EMAIL, Email)
        editor.apply()
        return true
    }

    fun loggedin(): Boolean {
        val sharedPreferences: SharedPreferences = Companion.ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return if (sharedPreferences.getString(KEY_NAME, null) != null) {
            true
        } else false
    }

    fun logout(): Boolean {
        val sharedPreferences: SharedPreferences = Companion.ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }

    val userID: Int
        get() {
            val sharedPreferences: SharedPreferences = Companion.ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt(KEY_ID, 0)
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: SharedPreferenceManager? = null
        private const val SHARED_PREF_NAME = "mysharedpref12"
        private const val KEY_ID = "userId"
        private const val KEY_NAME = "userName"
        private const val KEY_EMAIL = "email "
        @Synchronized
        fun getInstance(context: Context): SharedPreferenceManager? {
            if (instance == null) {
                instance = SharedPreferenceManager(context)
            }
            return instance
        }
    }
}