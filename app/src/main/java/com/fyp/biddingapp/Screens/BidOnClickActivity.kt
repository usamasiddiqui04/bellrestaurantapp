package com.fyp.biddingapp.Screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.fragments.BidAmountFragment
import com.fyp.biddingapp.fragments.ShowBidDataFragment
import kotlinx.android.synthetic.main.activity_bid_on_click.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class BidOnClickActivity : AppCompatActivity() {

    var bidData: BidListItem? = null
    var userID : Int = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_on_click)

        bidData = intent!!.getParcelableExtra("bidData")

        val imageUrl = "${Constants.URL_IMAGES}${bidData?.bidImage}"

        Glide.with(this).load(imageUrl).into(bidImage)

        userID = SharedPreferenceManager.getInstance(this).userID

        checkStatus.text = bidData?.bidStatus
        bidTitle.text = bidData?.bidTitle
        bidDuration.text = bidData?.bidDuration
        bidDescription.text = bidData?.bidDiscription
        bidMinAmount.text = "Rs ${bidData?.bidMinAmount.toString()}"

        addToFavourite.setOnClickListener {
            addToFavourite()
        }

        startBid.setOnClickListener {
            val bidAmountFragment = BidAmountFragment.newInstance()
            bidAmountFragment.setBidID(bidData?.id.toString())
            bidAmountFragment.show(supportFragmentManager , "BidAmountFragment")
        }

        showBidData.setOnClickListener {
            val showBidDataFragment = ShowBidDataFragment.newInstance()
            showBidDataFragment.setBidID(bidData?.id.toString() , bidData!!.bidTitle)
            showBidDataFragment.show(supportFragmentManager, "ShowBidDataFragment")
        }



    }

    private fun addToFavourite() {

        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_FAVOURITE_BIDS, Response.Listener { response ->
            try {
                val jsonObject = JSONObject(response)
                Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["bidId"] = bidData?.id.toString()
                params["userId"] = userID.toString()
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)

    }
}