package com.dropoutsolutions.bellrestaurantapp.LoginAndSignup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dropoutsolutions.bellrestaurantapp.Models.Constants;
import com.dropoutsolutions.bellrestaurantapp.Models.RequestHandler;
import com.dropoutsolutions.bellrestaurantapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText username , email , password , confrimpassword ;
    Button button ;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.ownername);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confrimpassword = findViewById(R.id.confrimpassword);
        button = findViewById(R.id.buttonregister);
        progressDialog = new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });

    }


    private void registration() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        final String UserName = username.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        String ConfrimPassword = confrimpassword.getText().toString().trim();

        progressDialog.setTitle("Register User");
        progressDialog.setMessage("Please wait it will take few seconds");
        progressDialog.setCancelable(false);
        progressDialog.show(); 
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();
                Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("userName" , UserName);
                params.put("email" , Email);
                params.put("password" , Password);
                params.put("createdAt", formattedDate);

                 return params ;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}