package com.fyp.biddingapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.adaptors.EachBidItemsAdaptor
import com.fyp.biddingapp.adaptors.FavouriteBidsAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.dataclass.EachBidListItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_show_bid_data.*
import kotlinx.android.synthetic.main.fragment_wishlist.*
import kotlinx.android.synthetic.main.fragment_wishlist.favouriteBidsRecyclerView
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap


class ShowBidDataFragment : BottomSheetDialogFragment() {

    var bidId : String? = null
    var bidName : String? = null
    private var listOfEachBidDataItem: ArrayList<EachBidListItem> = ArrayList()


    private val eachBidItemsAdaptor by lazy {

        EachBidItemsAdaptor(requireContext(), ArrayList())
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_bid_data, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = bidName
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerviewBidlistItem?.layoutManager = linearLayoutManager

        requestAllBidDataFromServer()

    }
    fun setBidID ( id : String , bidName : String)
    {
        this.bidId = id
        this.bidName = bidName
    }

    companion object {
        fun newInstance() =
                ShowBidDataFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    private fun requestAllBidDataFromServer() {
        val stringRequest: StringRequest = object : StringRequest(Method.POST,
                Constants.URL_GET_BIDDING_DATA, Response.Listener { response ->
            try {
                val pack = JSONArray(response)
                Toast.makeText(requireContext(), pack.toString(), Toast.LENGTH_SHORT).show()
                for (i in 0 until pack.length()) {
                    val getBidItems = pack.getJSONObject(i)
                    val userId = getBidItems.getInt("userId")
                    val firstName = getBidItems.getString("firstName")
                    val lastName = getBidItems.getString("lastName")
                    val bidId = getBidItems.getInt("id")
                    val bidTitle = getBidItems.getString("bidTitle")
                    val bidAmount = getBidItems.getString("bidAmount")
                    val profileImage = getBidItems.getString("profileImage")
                    val eachBidListItem = EachBidListItem(userId , bidId , firstName , lastName , bidTitle , bidAmount , profileImage)
                    listOfEachBidDataItem.add(eachBidListItem)
                }
                recyclerviewBidlistItem.adapter = eachBidItemsAdaptor
                eachBidItemsAdaptor.submitList(listOfEachBidDataItem)
            } catch (e: JSONException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["bidId"] = bidId!!.toString()
                return params
            }
        }
        RequestHandler.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }
}