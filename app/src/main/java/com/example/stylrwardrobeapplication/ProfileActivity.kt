package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stylrwardrobeapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Inflate the layout using View Binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Profile Details
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username") ?: "No username available"
                        binding.tvName.text = username
                    } else {
                        Log.e("ProfileActivity", "User document not found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("ProfileActivity", "Error fetching username: ", exception)
                }
        }

        // Set up RecyclerView

        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.add -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, CreateOutfitActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.home -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.wardrobe -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, WardrobeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.settings -> {
                    auth.signOut() // Sign out the user
                    val intent = Intent(this, Login::class.java) // Navigate to Login activity
                    startActivity(intent)
                    finish() // Close the current activity
                    true
                }

                else -> false
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(userId).collection("OOTD")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val photoList = mutableListOf<Pair<String, String>>()

                for (document in querySnapshot.documents) {
                    val ootdId = document.id
                    val imageUrl = document.getString("imageUrl") ?: continue
                    photoList.add(Pair(ootdId, imageUrl))
                }

                val layoutManager = GridLayoutManager(this, 3) // 3 columns
                binding.rvPhotos.layoutManager = layoutManager

                // Initialize adapter with the click listener
                val adapter = PhotoAdapter(photoList) { ootdId, photoUrl ->
                    // Handle the click here
                    val intent = Intent(this, PhotoDetailActivity::class.java)
                    intent.putExtra("ootdId", ootdId)
                    intent.putExtra("photoUrl", photoUrl)
                    startActivity(intent)
                }
                binding.rvPhotos.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileActivity", "Error fetching photos: ", exception)
            }
    }


}


