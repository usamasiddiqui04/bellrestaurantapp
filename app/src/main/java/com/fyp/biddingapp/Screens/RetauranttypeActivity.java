package com.fyp.biddingapp.Screens;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.biddingapp.R;


public class RetauranttypeActivity extends AppCompatActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retauranttype);

        next = findViewById(R.id.next);

    }
}