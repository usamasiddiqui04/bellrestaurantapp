package com.fyp.biddingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.catitemlist.view.*


class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
    fun bind(text: String, image: Int) {

        itemView.imageViewCategory.setImageResource(image)
        itemView.textNameCategory.text = text

    }
}