package com.fyp.biddingapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fyp.biddingapp.R;
import com.fyp.biddingapp.Screens.login.LoginActivity;
import com.fyp.biddingapp.Screens.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotomain = new Intent(SplashActivity.this , MainActivity.class);
                startActivity(gotomain);
                finish();
            }
        },3000);
    }

}