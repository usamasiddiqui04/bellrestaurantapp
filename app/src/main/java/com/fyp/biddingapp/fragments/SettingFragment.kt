package com.fyp.biddingapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.EditProfileActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
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
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
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