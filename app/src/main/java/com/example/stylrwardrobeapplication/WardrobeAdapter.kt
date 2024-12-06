package com.example.stylrwardrobeapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.stylrwardrobeapplication.databinding.ItemWardrobeBinding
import com.squareup.picasso.Picasso

class WardrobeAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<WardrobeAdapter.ImagesViewHolder>() {

    inner class ImagesViewHolder(var binding: ItemWardrobeBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val binding = ItemWardrobeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        with(holder.binding) {
            with(imageList[position]) {
                // Load image into ImageView
                // Glide.with(holder.itemView.context).load(this).into(ivImage)
                Picasso.get().load(this).into(itemImageView) // item image view from binding
            }
        }
    }

    override fun getItemCount(): Int = imageList.size
}
