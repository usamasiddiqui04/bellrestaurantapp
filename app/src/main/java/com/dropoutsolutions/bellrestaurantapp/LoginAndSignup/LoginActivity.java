package com.dropoutsolutions.bellrestaurantapp.LoginAndSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dropoutsolutions.bellrestaurantapp.Models.Constants;
import com.dropoutsolutions.bellrestaurantapp.Models.RequestHandler;
import com.dropoutsolutions.bellrestaurantapp.Models.SharedPreferenceManager;
import com.dropoutsolutions.bellrestaurantapp.R;
import com.dropoutsolutions.bellrestaurantapp.Screens.RestaurantdetailsActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView Register;
    Button buttonlogin ;

    private  TextInputEditText email , password ;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register = (TextView) findViewById(R.id.txt_registerhere);
        buttonlogin = findViewById(R.id.btnlogin);
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Checking login details");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void loginuser() {

        final String _email = email.getText().toString().trim();
        final String _password = password.getText().toString().trim();
        progressDialog.show();;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error"))
                    {
                        SharedPreferenceManager.getInstance(getApplicationContext()).UserLogin(
                                jsonObject.getInt("userId"),
                                jsonObject.getString("email"),
                                jsonObject.getString("userName")
                        );
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("userName") , Toast.LENGTH_SHORT).show();
                    }
                    else
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("email" , _email);
                params.put("password" , _password);

                return params ;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}