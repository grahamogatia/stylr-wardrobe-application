package com.example.stylrwardrobeapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class WardrobeAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<WardrobeAdapter.WardrobeViewHolder>() {

    class WardrobeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardrobeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wardrobe, parent, false)
        return WardrobeViewHolder(view)
    }

    override fun onBindViewHolder(holder: WardrobeViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position]) // Bind image resource
    }

    override fun getItemCount(): Int = imageList.size
}
