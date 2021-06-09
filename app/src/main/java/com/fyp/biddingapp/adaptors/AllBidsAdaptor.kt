package com.fyp.biddingapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fyp.biddingapp.R
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.viewholder.AllBidViewHolder
import com.fyp.biddingapp.viewholder.RecommendedViewHolder

class AllBidsAdaptor(
        private var context: Context, private var listOfAllBids: ArrayList<BidListItem>
) :
        RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return AllBidViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.allbiditemlayout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is AllBidViewHolder -> {
                holder.bind(listOfAllBids[position] , context)
                holder.itemView.setOnClickListener {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfAllBids.size
    }


    fun submitList(listOfAllBids: ArrayList<BidListItem>) {
        this.listOfAllBids.clear()
        this.listOfAllBids = listOfAllBids
        notifyDataSetChanged()
    }

}