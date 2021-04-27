package com.fyp.biddingapp.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.biddingapp.R;
import com.fyp.biddingapp.Screens.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;


public class UserDetailActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private Button cont;
    private TextInputEditText number;
    private int randomnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        cont = findViewById(R.id.next);
        number = findViewById(R.id.number);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDetailActivity.this, MainActivity.class));
            }
        });

        countryCodePicker = findViewById(R.id.countrycodepicker);


    }

}