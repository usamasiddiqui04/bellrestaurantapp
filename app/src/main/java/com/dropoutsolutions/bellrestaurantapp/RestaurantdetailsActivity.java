package com.dropoutsolutions.bellrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class RestaurantdetailsActivity extends AppCompatActivity {

    private CountryCodePicker countryCodePicker ;
    private Button cont ;
    private TextInputEditText number ;
    private int randomnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdetails);
        cont = findViewById(R.id.next);
        number = findViewById(R.id.number);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    // Construct data
//                    String apiKey = "apikey=" + "zQUgkVioBJM-iOVqh6NST7jcihnbAVVxbe84UxFooJ";
//                    Random random = new Random();
//                    randomnumber = random.nextInt(999999);
//                    String message = "&message=" + "Your OTP code is " + randomnumber ;
//                    String sender = "&sender=" + "TSN";
//                    String numbers = "&numbers=" + number.getText();
//
//                    // Send data
//                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
//                    String data = apiKey + numbers + message + sender;
//                    conn.setDoOutput(true);
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//                    conn.getOutputStream().write(data.getBytes("UTF-8"));
//                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                    final StringBuffer stringBuffer = new StringBuffer();
//                    String line;
//                    while ((line = rd.readLine()) != null) {
//                        stringBuffer.append(line);
//                    }
//                    rd.close();
//
//                    Toast.makeText(RestaurantdetailsActivity.this, "Send", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Toast.makeText(RestaurantdetailsActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }

                startActivity(new Intent(RestaurantdetailsActivity.this, RetauranttypeActivity.class));
            }
        });

        countryCodePicker = findViewById(R.id.countrycodepicker);



    }

}