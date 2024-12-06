package com.example.stylrwardrobeapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoAdapter(
    private val photoList: List<Pair<String, String>>, // Pair of ootdId and photoUrl
    private val onItemClick: (ootdId: String, photoUrl: String) -> Unit // Callback for click handling
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val (ootdId, photoUrl) = photoList[position]

        // Load the photo using Glide
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.imageView)

        // Set the click listener for the item
        holder.itemView.setOnClickListener {
            onItemClick(ootdId, photoUrl) // Trigger the callback with ootdId and photoUrl
        }
    }

    override fun getItemCount(): Int = photoList.size
}
