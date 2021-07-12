package com.fyp.biddingapp.adaptors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fyp.biddingapp.R
import com.fyp.biddingapp.Screens.BidOnClickActivity
import com.fyp.biddingapp.dataclass.BidListItem
import com.fyp.biddingapp.viewholder.RecommendedViewHolder

class RecommendedAdaptor(
        private var context: Context, private var listOfAllBids: ArrayList<BidListItem>
) :
        RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return RecommendedViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.recommendedlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is RecommendedViewHolder -> {
                holder.bind(listOfAllBids[position] , context)
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, BidOnClickActivity::class.java)
                    intent.putExtra("bidData", listOfAllBids[position])
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
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