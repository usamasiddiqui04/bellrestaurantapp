package com.fyp.biddingapp.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.bidsdetails.BidDetailsActivity
import com.fyp.biddingapp.Screens.userdetails.UserDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(),BidDetailsActivity::class.java))
        }
    }
}