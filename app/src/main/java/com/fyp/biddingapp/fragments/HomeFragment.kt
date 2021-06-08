package com.fyp.biddingapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.Models.RequestHandler
import com.fyp.biddingapp.Models.SharedPreferenceManager
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.AllBids
import com.fyp.biddingapp.Screens.BidDetailsActivity
import com.fyp.biddingapp.Screens.UserDetailActivity
import com.fyp.biddingapp.adaptors.CategoryAdaptors
import com.fyp.biddingapp.adaptors.RecommendedAdaptor
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException

class HomeFragment : Fragment() {

    private var listOfAllBids: ArrayList<BidListItem> = ArrayList()

    private val imageAdapter by lazy {

        CategoryAdaptors(context = requireContext(), ArrayList(), ArrayList())
    }

    private val recommendedAdapter by lazy {

        RecommendedAdaptor(context = requireContext(), ArrayList())
    }



    var userName: String? = null
    var wellcomeText: String? = null

    var listOfImages: ArrayList<Int>? = null
    var listOfText: ArrayList<String>? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), BidDetailsActivity::class.java))
        }

        userName = SharedPreferenceManager.getInstance(requireContext()).userName.toString()
        wellcomeText = text.text.toString()

        text.text = "$wellcomeText $userName"

        listOfText = ArrayList()
        listOfImages = ArrayList()

        listOfImages!!.apply {
            add(R.drawable.antiques)
            add(R.drawable.car)
            add(R.drawable.house)
            add(R.drawable.plot)
            add(R.drawable.jewellery)
        }
        listOfText!!.apply {
            add("Antiques")
            add("Car")
            add("House")
            add("Plot")
            add("Jewellery")
        }

        recyclerViewCategory.adapter = imageAdapter
        val categoryLinearLayoutManager = LinearLayoutManager(requireContext())
        categoryLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewCategory.layoutManager = categoryLinearLayoutManager
        imageAdapter.submitList(listOfImages!!, listOfText!!)

        val recommendedLinearLayoutManager = LinearLayoutManager(requireContext())
        recommendedLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewRecommended.layoutManager = recommendedLinearLayoutManager
        requestAllBidDataFromServer()

        textViewSeeAll.setOnClickListener {
            startActivity(Intent(requireContext(), AllBids::class.java))
        }
    }

    private fun requestAllBidDataFromServer() {
        val stringRequest = StringRequest(Request.Method.GET, Constants.URL_GET_ALL_BIDS,
                { response ->
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
                        recyclerViewRecommended.adapter = recommendedAdapter
                        recommendedAdapter.submitList(listOfAllBids)
                    } catch (e: JSONException) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }) { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }

        RequestHandler.getInstance(requireContext()).addToRequestQueue(stringRequest)

    }

}