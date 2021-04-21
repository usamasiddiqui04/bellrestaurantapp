package com.dropoutsolutions.biddingapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dropoutsolutions.biddingapp.LoginAndSignup.LoginActivity;
import com.dropoutsolutions.biddingapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotomain = new Intent(SplashActivity.this , LoginActivity.class);
                startActivity(gotomain);
                finish();
            }
        },3000);
    }

}