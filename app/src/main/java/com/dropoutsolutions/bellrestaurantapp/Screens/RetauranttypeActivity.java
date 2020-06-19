package com.dropoutsolutions.bellrestaurantapp.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dropoutsolutions.bellrestaurantapp.R;

public class RetauranttypeActivity extends AppCompatActivity {

    Button next ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retauranttype);

        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetauranttypeActivity.this , WorkinghoursActivity.class));
            }
        });

    }
}