package com.fyp.biddingapp.Screens.bidsdetails

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_bid_details.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*


class BidDetailsActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    var endcodedimage: String? = null

    var type = arrayOf("Automotive", "Clothing and Accessories", "Electronics", "Entertainment",
            "Event Tickets", "Food and Beverage", "For the Kids", "Health and Beauty",
            "Health and Fitness", "Home Improvement", "Hotels", "Jewelry and Watches", "Sports",
            "Specialty", "Furniture")
    var arrayAdapter: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_details)

        arrayAdapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, type)
        spinnerCategory.setAdapter(arrayAdapter)
        spinnerCategory.threshold = 1



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
        uploadImageToServer()
    }

    fun uploadImageToServer() {
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_UPLOAD_IMAGE, Response.Listener {
            response ->
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show() },
                Response.ErrorListener {
                    error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["bidImage"] = endcodedimage!!
                params["id"] = 11.toString()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}

