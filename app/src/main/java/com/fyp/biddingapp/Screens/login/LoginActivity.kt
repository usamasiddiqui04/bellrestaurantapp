package com.fyp.biddingapp.Screens.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.UserDetailActivity
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
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Register = findViewById<View>(R.id.txt_registerhere) as TextView
        buttonlogin = findViewById(R.id.btnlogin)
        email = findViewById(R.id.loginemail)
        password = findViewById(R.id.loginpassword)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Checking login details")
        progressDialog!!.setMessage("Please wait")
        progressDialog!!.setCancelable(false)
        buttonlogin!!.setOnClickListener(View.OnClickListener { loginuser() })
        Register!!.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }
    }

    private fun loginuser() {
        val _email = email!!.text.toString().trim { it <= ' ' }
        val _password = password!!.text.toString().trim { it <= ' ' }
        progressDialog!!.show()
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_LOGIN, Response.Listener { response ->
            progressDialog!!.dismiss()
            try {
                val jsonObject = JSONObject(response)
                if (!jsonObject.getBoolean("error")) {
                    SharedPreferenceManager.getInstance(applicationContext).UserLogin(
                            jsonObject.getInt("userId"),
                            jsonObject.getString("email"),
                            jsonObject.getString("userName")
                    )
                    progressDialog!!.dismiss()
                    startActivity(Intent(this@LoginActivity, UserDetailActivity::class.java))
                } else progressDialog!!.dismiss()
                Toast.makeText(this@LoginActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                progressDialog!!.dismiss()
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            progressDialog!!.dismiss()
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