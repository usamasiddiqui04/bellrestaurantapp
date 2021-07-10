package com.fyp.biddingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bid_amount.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class BidAmountFragment : BottomSheetDialogFragment() {

    var id : String? = null
    var userID : String?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
        userID = SharedPreferenceManager.getInstance(context).userID.toString()

        buttonDone.setOnClickListener {
            if (bidMinAmount.text!!.isEmpty())
            {
                Toast.makeText(context, "Please enter bid amount", Toast.LENGTH_SHORT).show()
            }
            else
            {
                addDataToServer()
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bid_amount, container, false)
    }


    companion object {
        fun newInstance() =
                BidAmountFragment().apply {
                }
    }

    fun setBidID (id : String){
        this.id = id
    }

    private fun addDataToServer() {

        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_ENTER_BID_AMOUNT, Response.Listener { response ->
            try {
                val jsonObject = JSONObject(response)
                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userId"] = userID.toString()
                params["bidId"] = id!!
                params["bidAmount"] = bidMinAmount.text.toString()
                return params
            }
        }
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest)

    }
}