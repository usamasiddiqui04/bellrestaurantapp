package com.fyp.biddingapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fyp.biddingapp.R
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.dataclass.EachBidListItem
import com.fyp.biddingapp.viewholder.AllBidViewHolder
import com.fyp.biddingapp.viewholder.EachBidItemViewHolder
import com.fyp.biddingapp.viewholder.FavouriteBidsViewHolder
import com.fyp.biddingapp.viewholder.RecommendedViewHolder

class EachBidItemsAdaptor(
        private var context: Context, private var listOfAllBids: ArrayList<EachBidListItem>
) :
        RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return EachBidItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.biddata_itemlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is EachBidItemViewHolder -> {
                holder.bind(listOfAllBids[position] , context)
                holder.itemView.setOnClickListener {
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfAllBids.size
    }


    fun submitList(listOfAllBids: ArrayList<EachBidListItem>) {
        this.listOfAllBids.clear()
        this.listOfAllBids = listOfAllBids
        notifyDataSetChanged()
    }

}