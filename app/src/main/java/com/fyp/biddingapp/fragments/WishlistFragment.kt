package com.fyp.biddingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.adaptors.AllBidsAdaptor
import com.fyp.biddingapp.adaptors.FavouriteBidsAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.activity_all_bids.*
import kotlinx.android.synthetic.main.activity_all_bids.allBidsRecyclerView
import kotlinx.android.synthetic.main.fragment_wishlist.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class WishlistFragment : Fragment() {

    private var listOfAllBids: ArrayList<BidListItem> = ArrayList()

    private val favouriteBidsAdaptor by lazy {

        FavouriteBidsAdaptor(requireContext(), ArrayList())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        favouriteBidsRecyclerView?.layoutManager = linearLayoutManager
        requestAllBidDataFromServer()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }


    private fun requestAllBidDataFromServer() {
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_GET_ALL_FAVOURITE, Response.Listener { response ->
            try {
                val pack = JSONArray(response)
                Toast.makeText(requireContext(), pack.toString(), Toast.LENGTH_SHORT).show()
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
                favouriteBidsRecyclerView.adapter = favouriteBidsAdaptor
                favouriteBidsAdaptor.submitList(listOfAllBids)
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