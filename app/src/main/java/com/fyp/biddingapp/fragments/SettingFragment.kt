package com.fyp.biddingapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.BarChartActivity
import com.fyp.biddingapp.Screens.EditProfileActivity
import com.fyp.biddingapp.Screens.PieChartActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_bid_details.*
import kotlinx.android.synthetic.main.fragment_setting.*
import org.json.JSONArray
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {

    var bitmap: Bitmap? = null
    var endcodedimage = ""
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
    var profileImage : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllUserDataFromServer()

        editProfile.setOnClickListener {

            val intent = Intent(context , EditProfileActivity::class.java)
            intent.putExtra("firstName" , firstName)
            intent.putExtra("lastName" , lastName)
            intent.putExtra("phoneNumber" , phoneNumber)
            intent.putExtra("gender" , gender)
            intent.putExtra("dob" , dob)
            intent.putExtra("country" , country)
            intent.putExtra("description" , description)
            intent.putExtra("city" , city)
            intent.putExtra("cnic" , cnic)
            intent.putExtra("province" , province)
            startActivity(intent)
        }

        pieChart.setOnClickListener {
            val intent = Intent(context , PieChartActivity::class.java)
            startActivity(intent)
        }

        barChart.setOnClickListener {
            val intent = Intent(context , BarChartActivity::class.java)
            startActivity(intent)
        }

        userProfileImage.setOnClickListener {
            Dexter.withActivity(requireActivity())
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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val filepath = data.data
            try {
                val inputStream = context?.contentResolver?.openInputStream(filepath!!)
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

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    private fun uploadImageToServer(){
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_UPLOAD_IMAGE, Response.Listener { response ->
            try {
                val pack = JSONArray(response)
                Toast.makeText(requireContext(), pack.toString(), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userId"] = SharedPreferenceManager.getInstance(requireContext()).userID.toString()
                params["profileImage"] = endcodedimage
                return params
            }
        }
        RequestHandler.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun getAllUserDataFromServer() {

        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_GET_ALL_USER_DATA, Response.Listener { response ->
            try {
                val pack = JSONArray(response)
                Toast.makeText(requireContext(), pack.toString(), Toast.LENGTH_SHORT).show()
                for (i in 0 until pack.length()) {
                    val getBidItems = pack.getJSONObject(i)
                    firstName = getBidItems.getString("firstName")
                    lastName = getBidItems.getString("lastName")
                    phoneNumber = getBidItems.getString("phoneNumber")
                    gender = getBidItems.getString("gender")
                    dob = getBidItems.getString("dob")
                    profileImage = getBidItems.getString("profileImage")
                    country = getBidItems.getString("country")
                    description = getBidItems.getString("description")
                    city = getBidItems.getString("city")
                    cnic = getBidItems.getString("cnic")
                    province = getBidItems.getString("province")


                    userFullName.text = "$firstName $lastName"
                    userName.text = "$firstName $lastName"
                    userPhoneNumber.text = phoneNumber
                    userGender.text = gender
                    userDob.text = dob
                    userCountry.text = country
                    userDiscription.text = description
                    userCity.text = city
                    userCnic.text = cnic
                    userProvince.text = province
                    userEmail.text = SharedPreferenceManager.getInstance(requireContext()).userEmail.toString()
                }
            } catch (e: JSONException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userId"] = SharedPreferenceManager.getInstance(requireContext()).userID.toString()
                return params
            }
        }
        RequestHandler.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

}