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
        binding.tvName.text = "Graham Ogatia"
        binding.tvUsername.text = "@grhmxoxo"

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
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Log.e("ProfileActivity", "User not logged in!")
            return
        }

        // Fetch "outfits" collection for the logged-in user
        db.collection("users").document(userId).collection("OOTD")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val photoUrls = mutableListOf<String>()

                for (document in querySnapshot.documents) {
                    val photoUrl = document.getString("imageUrl") // Match Firestore field name
                    if (photoUrl != null) {
                        photoUrls.add(photoUrl)
                    }
                }

                if (photoUrls.isEmpty()) {
                    Log.w("ProfileActivity", "No photos found in the user's outfits collection.")
                }

                // Set up RecyclerView
                val layoutManager = GridLayoutManager(this, 3) // 3 columns
                binding.rvPhotos.layoutManager = layoutManager

                val adapter = PhotoAdapter(photoUrls)
                binding.rvPhotos.adapter = adapter

                Log.d("ProfileActivity", "Photos loaded successfully: ${photoUrls.size} items.")
            }
            .addOnFailureListener { exception ->
                Log.e("ProfileActivity", "Error fetching photos: ", exception)

            }
    }

    }



