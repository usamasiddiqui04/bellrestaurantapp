package com.fyp.biddingapp.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_bid_details.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class BidDetailsActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    var endcodedimage = ""

    var userBidStatus: String? = null
    var userBidTitle: String? = null
    var userBidStartDate: String? = null
    var userBidDescription: String? = null
    var userBidMinAmount: String? = null
    var userBidDuration: String? = null
    var userBidCategory: String? = null
    var userBidEndDate: String? = null
    var userBidVerifiedAt: String? = null
    var userId: String? = null
    private val progressDialog = CustomProgressDialog()

    val myCalendar = Calendar.getInstance()


    var type = arrayOf("Automotive", "Clothing and Accessories", "Electronics", "Entertainment",
            "Event Tickets", "Food and Beverage", "For the Kids", "Health and Beauty",
            "Health and Fitness", "Home Improvement", "Hotels", "Jewelry and Watches", "Sports",
            "Specialty", "Furniture")


    var arrayAdapter: ArrayAdapter<String>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_details)

        arrayAdapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, type)
        spinnerCategory.setAdapter(arrayAdapter)
        spinnerCategory.threshold = 1

        val startdate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateStartDateEditText()
        }

        val enddate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateEndDateEditText()
        }

        userSaveBidDetails.setOnClickListener {
            checkValidation()
        }


        cardviewImage.setOnClickListener {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            startActivityForResult(Intent.createChooser(intent, "Select Image"), 1)
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) {}
                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {}
                    }).check()
        }

        bidStartDate.setOnClickListener {
            DatePickerDialog(this, startdate, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        bidEndDate.setOnClickListener {
            DatePickerDialog(this, enddate, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkValidation() {

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val now: LocalDateTime = LocalDateTime.now()

        userId = SharedPreferenceManager.getInstance(applicationContext).userID.toString()
        userBidStatus = "active"
        userBidTitle = bidAddTitle.text.toString()
        userBidStartDate = bidStartDate.text.toString()
        userBidDescription = bidDescription.text.toString()
        userBidMinAmount = bidMinAmount.text.toString()
        userBidEndDate = bidEndDate.text.toString()
        userBidDuration = findDifference(userBidStartDate, userBidEndDate)
        userBidCategory = spinnerCategory.text.toString()
        userBidVerifiedAt = dtf.format(now).toString()

        if (userBidTitle!!.isEmpty()) {
            bidAddTitle.error = "Please enter title"
            return
        }
        if (userBidStartDate!!.isEmpty()) {
            bidAddTitle.error = "Please enter start date"
            return
        }
        if (userBidDescription!!.isEmpty()) {
            bidDescription.error = "Please enter details"
            return
        }
        if (userBidMinAmount!!.isEmpty()) {
            bidMinAmount.error = "Please enter minimum amount of bid"
            return
        }
        if (userBidEndDate!!.isEmpty()) {
            bidEndDate.error = "Please enter end date"
            return
        }
        if (userBidCategory!!.isEmpty()) {
            spinnerCategory.error = "Please enter category"
            return
        }

        if (endcodedimage.isEmpty()) {
            Toast.makeText(this, "please sekect image from gallery", Toast.LENGTH_SHORT).show()
            return
        }
        uploadDataToServer()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val filepath = data.data
            try {
                val inputStream = contentResolver.openInputStream(filepath!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                bidImageView.setImageBitmap(bitmap)
                imageStore(bitmap!!)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun imageStore(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imagebytes = stream.toByteArray()
        endcodedimage = Base64.encodeToString(imagebytes, Base64.DEFAULT)
    }

    @SuppressLint("SimpleDateFormat")
    fun findDifference(
            start_date: String?,
            end_date: String?,
    ): String {

        var duration: String? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        try {
            val startDate: Date = sdf.parse(start_date)
            val endDate: Date = sdf.parse(end_date)
            val timeDifference = endDate.time - startDate.time
            val secondDifference = ((timeDifference / 1000) % 60)
            val minuteDifference = ((timeDifference / (1000 * 60)) % 60)
            val hoursDifference = ((timeDifference / (1000 * 60 * 60)) % 24)
            val yearsDifference = (timeDifference / (1000L * 60 * 60 * 24 * 365))
            val daysDifference = ((timeDifference / (1000 * 60 * 60 * 24)) % 365)
            duration = (daysDifference.toString()
                    + " days, "
                    + hoursDifference
                    + " hours, "
                    + minuteDifference
                    + " minutes")

            return duration
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    private fun updateStartDateEditText() {
        val myFormat = "yyyy-MM-dd" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        bidStartDate.setText(sdf.format(myCalendar.time))
    }

    private fun updateEndDateEditText() {
        val myFormat = "yyyy-MM-dd" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)

        bidEndDate.setText(sdf.format(myCalendar.time))
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun uploadDataToServer() {
        progressDialog.show(this, "please wait...")
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_BID_DETAILS, Response.Listener { response ->
            try {
                val jsonObject = JSONObject(response)
                if (!jsonObject.getBoolean("error")) {
                    progressDialog.dialog.dismiss()
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
                params["userId"] = userId!!
                params["bidStatus"] = userBidStatus!!
                params["bidTitle"] = userBidTitle!!
                params["bidStartDate"] = userBidStartDate!!
                params["bidDiscription"] = userBidDescription!!
                params["bidMinAmount"] = userBidMinAmount!!
                params["bidDuration"] = userBidDuration!!
                params["bidCategory"] = userBidCategory!!
                params["bidEndDate"] = userBidEndDate!!
                params["bidVerifiedAt"] = userBidVerifiedAt!!
                params["bidImage"] = endcodedimage
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)

    }
}

