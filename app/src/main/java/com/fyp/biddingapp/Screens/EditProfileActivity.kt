package com.fyp.biddingapp.Screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import kotlinx.android.synthetic.main.activity_userdetails.*
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {


    var firstName : String? = null
    var lastName : String? = null
    var phoneNumber : String? = null
    var gender : String? = null
    var dob : String? = null
    var country : String? = null
    var description : String? = null
    var city : String? = null
    var cnic : String? = null
    var province : String? = null

    val myCalendar = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)

        welcome.text = "Update Profile"
        cont.visibility = View.GONE

        firstName = intent.getStringExtra("firstName")
        lastName = intent.getStringExtra("lastName")
        phoneNumber = intent.getStringExtra("phoneNumber")
        gender = intent.getStringExtra("gender")
        dob = intent.getStringExtra("dob")
        country = intent.getStringExtra("country")
        description = intent.getStringExtra("description")
        city = intent.getStringExtra("city")
        cnic = intent.getStringExtra("cnic")
        province = intent.getStringExtra("province")


        userFirstName.setText(firstName)
        userLastName.setText(lastName)
        userPhoneNumber.setText(phoneNumber)
        userGender.setText(gender)
        userCountry.setCountryForNameCode(country)
        userDescription.setText(description)
        userCity.setText(city)
        userCnic.setText(cnic)
        userProvince.setText(province)
        userDateOfBirth.setText(dob)

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
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
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
            updateAllUserData()
        }

    }

    private fun updateAllUserData() {

        val stringRequest: StringRequest = object : StringRequest(Method.GET,
                Constants.URL_UPDATE_USER_DATA, Response.Listener { response ->
            try {
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                params["userId"] = SharedPreferenceManager.getInstance(applicationContext).userID.toString()
                params["firstName"] = userFirstName.text.toString()
                params["firstName"] = userFirstName.text.toString()
                params["lastName"] = userLastName.text.toString()
                params["phoneNumber"] = userPhoneNumber.text.toString()
                params["gender"] = userGender.text.toString()
                params["dob"] = userDateOfBirth.text.toString()
                params["country"] = userCountry.selectedCountryEnglishName.toString()
                params["description"] = userDescription.text.toString()
                params["city"] = userCity.text.toString()
                params["cnic"] = userCnic.text.toString()
                params["province"] = userProvince.text.toString()




                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        userDateOfBirth.setText(sdf.format(myCalendar.time))
    }

}