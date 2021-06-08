package com.fyp.biddingapp.viewholder
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fyp.biddingapp.Models.Constants
import com.fyp.biddingapp.dataclass.BidListItem
import kotlinx.android.synthetic.main.recommendedlist.view.*


class RecommendedViewHolder(itemView: View) : ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(bidItem: BidListItem, context: Context) {

        val imageUrl = "${Constants.URL_IMAGES}${bidItem.bidImage}"
        Glide.with(context).load(imageUrl).into(itemView.imageViewRecommended)

        itemView.textViewTitle.text = bidItem.bidTitle
        itemView.textViewBidMinAmount.text = "Min amount : ${bidItem.bidMinAmount}"

    }
}