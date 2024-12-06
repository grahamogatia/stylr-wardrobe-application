package com.example.stylrwardrobeapplication


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PostsAdapter(private val photos: List<Int>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.imageView.setImageResource(photos[position])

        // Set click listener for each photo
        holder.itemView.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, PostsActivity::class.java).apply {
                putExtra("photoResId", photos[position]) // Pass photo resource ID
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = photos.size
}
