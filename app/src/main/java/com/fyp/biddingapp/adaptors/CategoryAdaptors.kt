package com.fyp.biddingapp.adaptors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fyp.biddingapp.R
import com.fyp.biddingapp.viewholder.ImageViewHolder

class CategoryAdaptors(
        private var context: Context, private var listOfImages: ArrayList<Int>,
        private var listOfText: ArrayList<String>,
) :
        RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.catitemlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> {
                holder.bind(listOfText[position] , listOfImages[position])
                holder.itemView.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }


    fun submitList(listOfImages: ArrayList<Int>, listOfText: ArrayList<String>) {
        this.listOfImages = listOfImages
        this.listOfText = listOfText
        notifyDataSetChanged()
    }

}