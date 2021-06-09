package com.fyp.biddingapp.viewholder
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.allbiditemlayout.view.*
import kotlinx.android.synthetic.main.recommendedlist.view.*


class FavouriteBidsViewHolder(itemView: View) : ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(bidItem: BidListItem, context: Context) {

        val imageUrl = "${Constants.URL_IMAGES}${bidItem.bidImage}"
        Glide.with(context).load(imageUrl).into(itemView.imageViewAllBids)

        itemView.textViewTitleAllBids.text = bidItem.bidTitle
        itemView.textViewBidMinAmountAllbids.text = "Min amount : ${bidItem.bidMinAmount}"

    }
}