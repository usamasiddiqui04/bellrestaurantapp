package com.fyp.biddingapp.viewholder
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.dataclass.EachBidListItem
import kotlinx.android.synthetic.main.allbiditemlayout.view.*
import kotlinx.android.synthetic.main.biddata_itemlist.view.*
import kotlinx.android.synthetic.main.recommendedlist.view.*


class EachBidItemViewHolder(itemView: View) : ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(bidItem: EachBidListItem, context: Context) {

        itemView.userName.text = "${bidItem.firstName} ${bidItem.lastName}"
        itemView.bidTitle.text = bidItem.bidTitle
        itemView.bidAmount.text = "Rs ${bidItem.bidAMount}"
    }
}