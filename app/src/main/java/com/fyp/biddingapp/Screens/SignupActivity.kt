package com.fyp.biddingapp.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.loopdeck.progressbar.CustomProgressDialog
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : AppCompatActivity() {
    var username: TextInputEditText? = null
    var email: TextInputEditText? = null
    var password: TextInputEditText? = null
    var confrimpassword: TextInputEditText? = null
    var button: Button? = null
    private val progressDialog = CustomProgressDialog()
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        username = findViewById(R.id.ownername)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confrimpassword = findViewById(R.id.confrimpassword)
        button = findViewById(R.id.buttonregister)
        button!!.setOnClickListener(View.OnClickListener { registration() })

        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    @SuppressLint("SimpleDateFormat")
    private fun registration() {
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = df.format(c.time)
        val UserName = username!!.text.toString().trim { it <= ' ' }
        val Email = email!!.text.toString().trim { it <= ' ' }
        val Password = password!!.text.toString().trim { it <= ' ' }


        progressDialog.show(this, "Registering please wait...")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_REGISTER, Response.Listener { response ->
            try {
                progressDialog.dialog.dismiss()
                val jsonObject = JSONObject(response)
                Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                progressDialog.dialog.dismiss()
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            progressDialog.dialog.dismiss()
            Toast.makeText(this@SignupActivity, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userName"] = UserName
                params["email"] = Email
                params["password"] = Password
                params["createdAt"] = formattedDate
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }


}