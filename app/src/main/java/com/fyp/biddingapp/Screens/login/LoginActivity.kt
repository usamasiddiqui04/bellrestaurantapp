package com.fyp.biddingapp.Screens.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.loopdeck.progressbar.CustomProgressDialog
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.userdetails.UserDetailActivity
import com.fyp.biddingapp.Screens.signup.SignupActivity
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {
    var Register: TextView? = null
    var buttonlogin: Button? = null
    private var email: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private val progressDialog = CustomProgressDialog()

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Register = findViewById<View>(R.id.txt_registerhere) as TextView
        buttonlogin = findViewById(R.id.btnlogin)
        email = findViewById(R.id.loginemail)
        password = findViewById(R.id.loginpassword)
        buttonlogin!!.setOnClickListener(View.OnClickListener { loginuser() })
        Register!!.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun loginuser() {
        val _email = email!!.text.toString().trim { it <= ' ' }
        val _password = password!!.text.toString().trim { it <= ' ' }
        progressDialog.show(this, "logging please wait...")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_LOGIN, Response.Listener { response ->
            progressDialog.dialog.dismiss()
            try {
                val jsonObject = JSONObject(response)
                if (!jsonObject.getBoolean("error")) {
                    SharedPreferenceManager.getInstance(applicationContext).UserLogin(
                            jsonObject.getInt("userId"),
                            jsonObject.getString("email"),
                            jsonObject.getString("userName")
                    )
                    progressDialog.dialog.dismiss()
                    startActivity(Intent(this@LoginActivity, UserDetailActivity::class.java))
                } else progressDialog.dialog.dismiss()
                Toast.makeText(this@LoginActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                progressDialog.dialog.dismiss()
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            progressDialog.dialog.dismiss()
            Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = _email
                params["password"] = _password
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }
}