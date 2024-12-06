package com.example.stylrwardrobeapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.stylrwardrobeapplication.databinding.ActivityPhotoDetailsBinding
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var shareDialog: ShareDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Facebook ShareDialog
        shareDialog = ShareDialog(this)

        // Retrieve data passed from the RecyclerView item click
        val photoUrl = intent.getStringExtra("photoUrl")
        val ootdId = intent.getStringExtra("ootdId")

        if (photoUrl == null || ootdId == null) {
            Toast.makeText(this, "Invalid data passed", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load the photo
        Glide.with(this).load(photoUrl).into(binding.ivPhoto)

        // Load existing data for rating and description
        loadOOTDData(ootdId)

        // Save data on change
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            saveOOTDData(ootdId, rating, binding.etDescription.text.toString())
        }

        binding.btnSave.setOnClickListener {
            val description = binding.etDescription.text.toString()
            val rating = binding.ratingBar.rating
            saveOOTDData(ootdId, rating, description)
        }

        // Add Share to Facebook functionality
        binding.btnShare.setOnClickListener {
            if (!photoUrl.isNullOrEmpty()) {
                sharePhotoToFacebook(photoUrl)
            } else {
                Toast.makeText(this, "Photo URL is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadOOTDData(ootdId: String) {
        if (userId == null) return

        db.collection("users")
            .document(userId)
            .collection("OOTD")
            .document(ootdId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val rating = document.getDouble("rating") ?: 0.0
                    val description = document.getString("description") ?: ""

                    binding.ratingBar.rating = rating.toFloat()
                    binding.etDescription.setText(description)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PhotoDetailActivity", "Error loading OOTD data", exception)
            }
    }

    private fun saveOOTDData(ootdId: String, rating: Float, description: String) {
        if (userId == null) return

        val ootdData = hashMapOf(
            "rating" to rating,
            "description" to description
        )

        db.collection("users")
            .document(userId)
            .collection("OOTD")
            .document(ootdId)
            .update(ootdData as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.e("PhotoDetailActivity", "Error saving OOTD data", exception)
            }
    }

    private fun sharePhotoToFacebook(photoUrl: String) {


        val linkContent = ShareLinkContent.Builder()
            .setContentUrl(Uri.parse("https://www.youtube.com/"))
            .setQuote("Useful link")
            .build()

        // Check if ShareLinkContent can be shown and display it
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            shareDialog.show(linkContent)
        }
    }
    }
      /*  val photo = SharePhoto.Builder()
            .setImageUrl(android.net.Uri.parse(photoUrl))
            .build()

        val content = SharePhotoContent.Builder()
            .addPhoto(photo)
            .build()

        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            shareDialog.show(content)
        } else {
            Toast.makeText(this, "Facebook sharing is not supported on this device.", Toast.LENGTH_SHORT).show()
        }
    }
}
*/