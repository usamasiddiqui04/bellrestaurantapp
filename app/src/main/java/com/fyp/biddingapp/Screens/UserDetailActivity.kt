package com.fyp.biddingapp.Screens

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_userdetails.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class UserDetailActivity : AppCompatActivity() {

    var firstName: String? = null
    var lastName: String? = null
    var phoneNumber: String? = null
    var gender: String? = null
    var dateOfBirth: String? = null
    var country: String? = null
    var description: String? = null
    var city: String? = null
    var cnic: String? = null
    var province: String? = null

    private val progressDialog = CustomProgressDialog()

    val myCalendar = Calendar.getInstance()


    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)

        userCnic.addTextChangedListener(object : TextWatcher {
            var len = 0
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = userCnic.text.toString()
                if (str.length == 5 && len < str.length || str.length == 13 && len < str.length) {
                    userCnic.append("-")
                }
            }

            override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int,
                    after: Int,
            ) {
                val str = userCnic.text.toString()
                len = str.length
            }

            override fun afterTextChanged(s: Editable) {
            }
        })


        val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        userDateOfBirth.setOnClickListener {
            DatePickerDialog(this, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]).show()
        }



        userSaveDetails.setOnClickListener {
            checkValidility()
        }


    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun uploadDataToServer() {
        progressDialog.show(this, "please wait...")


        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_DETAILS, Response.Listener { response ->
            try {
                val jsonObject = JSONObject(response)
                if (!jsonObject.getBoolean("error")) {
                    progressDialog.dialog.dismiss()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            progressDialog.dialog.dismiss()
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userId"] = SharedPreferenceManager.getInstance(applicationContext).userID.toString()
                params["firstName"] = firstName.toString()
                params["lastName"] = lastName.toString()
                params["phoneNumber"] = phoneNumber.toString()
                params["gender"] = gender.toString()
                params["dob"] = userDateOfBirth.text.toString()
                params["country"] = country.toString()
                params["description"] = description.toString()
                params["city"] = city.toString()
                params["cnic"] = cnic.toString()
                params["province"] = province.toString()
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun checkValidility() {

        firstName = userFirstName.text.toString()
        lastName = userLastName.text.toString()
        phoneNumber = userPhoneNumber.text.toString()
        gender = userGender.text.toString()
        country = userCountry.selectedCountryEnglishName.toString()
        description = userDescription.text.toString()
        city = userCity.text.toString()
        cnic = userCnic.text.toString()
        province = userProvince.text.toString()


        if (firstName!!.isEmpty()) {
            userFirstName.error = "Please enter your first name"
            return
        }
        if (lastName!!.isEmpty()) {
            userLastName.error = "Please enter your last name"
            return
        }
        if (phoneNumber!!.length > 12) {
            userPhoneNumber.error = "Please enter correct number (03123456778)"
            return
        }
        if (gender!!.isEmpty()) {
            userGender.error = "Please enter your gender"
            return
        }
        if (country!!.isEmpty())
            country = userCountry.defaultCountryName.toString()
        if (description!!.isEmpty()) {
            userDescription.error = "Please enter description"
            return
        }
        if (city!!.isEmpty()) {
            userCity.error = "Please enter city"
            return
        }
        if (cnic!!.isEmpty()) {
            userCnic.error = "Please enter 13 digit CNIC without dashes"
            return
        }

        if (province!!.isEmpty()) {
            userProvince.error = "Please enter province"
            return
        }
        uploadDataToServer()

    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        userDateOfBirth.setText(sdf.format(myCalendar.time))
    }

}