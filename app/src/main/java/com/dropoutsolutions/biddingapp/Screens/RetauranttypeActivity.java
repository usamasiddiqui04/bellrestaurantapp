package com.dropoutsolutions.biddingapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dropoutsolutions.biddingapp.R;

public class RetauranttypeActivity extends AppCompatActivity {

    Button next ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retauranttype);

        next = findViewById(R.id.next);

    }
}