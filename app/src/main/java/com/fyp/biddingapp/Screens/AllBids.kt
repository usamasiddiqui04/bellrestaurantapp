package com.fyp.biddingapp.Screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.R
import com.fyp.biddingapp.adaptors.AllBidsAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.activity_all_bids.*
import org.json.JSONArray
import org.json.JSONException

class AllBids : AppCompatActivity() {
    private var listOfAllBids: ArrayList<BidListItem> = ArrayList()



    private val allBidsAdaptor by lazy {

        AllBidsAdaptor(applicationContext, ArrayList())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_bids)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        allBidsRecyclerView.layoutManager = linearLayoutManager
        requestAllBidDataFromServer()
    }

    private fun requestAllBidDataFromServer() {
        val stringRequest = StringRequest(Request.Method.GET, Constants.URL_GET_ALL_BIDS,
                { response ->
                    try {
                        val pack = JSONArray(response)
                        Toast.makeText(this, pack.toString(), Toast.LENGTH_SHORT).show()
                        for (i in 0 until pack.length()) {
                            val getBidItems = pack.getJSONObject(i)
                            val id = getBidItems.getInt("id")
                            val userId = getBidItems.getInt("userId")
                            val bidStatus = getBidItems.getString("bidStatus")
                            val bidTitle = getBidItems.getString("bidTitle")
                            val bidImage = getBidItems.getString("bidImage")
                            val bidStartDate = getBidItems.getString("bidStartDate")
                            val bidDiscription = getBidItems.getString("bidDiscription")
                            val bidMinAmount = getBidItems.getInt("bidMinAmount")
                            val bidDuration = getBidItems.getString("bidDuration")
                            val bidCategory = getBidItems.getString("bidCategory")
                            val bidEndDate = getBidItems.getString("bidEndDate")
                            val bidVerifiedAt = getBidItems.getString("bidVerifiedAt")
                            val bidDataItem = BidListItem(bidCategory, bidDiscription, bidDuration, bidEndDate,
                                    bidImage, bidMinAmount, bidStartDate, bidStatus, bidTitle, bidVerifiedAt, id, userId)
                            listOfAllBids.add(bidDataItem)
                        }
                        allBidsRecyclerView.adapter = allBidsAdaptor
                        allBidsAdaptor.submitList(listOfAllBids)
                    } catch (e: JSONException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)

    }



}