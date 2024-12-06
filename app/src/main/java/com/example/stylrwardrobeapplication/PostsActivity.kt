

package com.example.stylrwardrobeapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.facebook.share.widget.ShareDialog

class PostsActivity : AppCompatActivity() {

    private lateinit var shareDialog: ShareDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        // Initialize ShareDialog
        shareDialog = ShareDialog(this)

        // Get data passed from the adapter
        val photoResId = intent.getIntExtra("photoResId", -1)

        // Set up views
        val imageView: ImageView = findViewById(R.id.imageView)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val description: TextView = findViewById(R.id.description)

        // Set the image and sample content
        imageView.setImageResource(photoResId)
        description.text = "This is a sample description for the photo."

        // Example: RatingBar logic (if needed)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Handle rating changes
        }

        // Set up Facebook Share Button
        val shareButton: View = findViewById(R.id.shareButton)
        shareButton.setOnClickListener {
            // Implement Facebook share logic here
        }
    }
}
