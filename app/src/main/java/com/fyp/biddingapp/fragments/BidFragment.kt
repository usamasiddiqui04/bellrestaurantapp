package com.fyp.biddingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.adaptors.FavouriteBidsAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.fragment_bid.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class BidFragment : Fragment() {

    private var listOfAllBids: ArrayList<BidListItem> = ArrayList()

    private val getUserBidAdapter by lazy {

        FavouriteBidsAdaptor(requireContext(), ArrayList())
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        userBidsRecyclerView?.layoutManager = linearLayoutManager
        requestAllBidDataFromServer()
    }

    private fun requestAllBidDataFromServer() {
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_GET_ALL_USER_BID, Response.Listener { response ->
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
                userBidsRecyclerView.adapter = getUserBidAdapter
                getUserBidAdapter.submitList(listOfAllBids)
            } catch (e: JSONException) {
            }
        }, Response.ErrorListener { error ->
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