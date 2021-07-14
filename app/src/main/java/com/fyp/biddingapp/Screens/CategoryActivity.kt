package com.fyp.biddingapp.Screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.R
import com.fyp.biddingapp.adaptors.AllBidsAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.activity_category.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set

class CategoryActivity : AppCompatActivity() {

    private var BidCategory : String? = null

    private val allBidsAdaptor by lazy {

        AllBidsAdaptor(applicationContext, ArrayList())
    }
    private var listOfAllBids: ArrayList<BidListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        BidCategory = intent.getStringExtra("category")


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        allBidsCategoryRecyclerView.layoutManager = linearLayoutManager
        requestAllBidDataFromServer()
    }

    private fun requestAllBidDataFromServer() {
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_GET_BID_BY_CATEGORY, Response.Listener { response ->
            try {
                val pack = JSONArray(response)
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
                allBidsCategoryRecyclerView.adapter = allBidsAdaptor
                allBidsAdaptor.submitList(listOfAllBids)
            } catch (e: JSONException) {
            }
        }, Response.ErrorListener { error ->
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["bidCategory"] = BidCategory.toString()
                return params
            }
        }
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest)
    }
}