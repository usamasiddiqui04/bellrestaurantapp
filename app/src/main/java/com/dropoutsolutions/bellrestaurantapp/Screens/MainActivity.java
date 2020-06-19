package com.dropoutsolutions.bellrestaurantapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dropoutsolutions.bellrestaurantapp.LoginAndSignup.LoginActivity;
import com.dropoutsolutions.bellrestaurantapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gotomain = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(gotomain);
                finish();
            }
        },3000);
    }

}