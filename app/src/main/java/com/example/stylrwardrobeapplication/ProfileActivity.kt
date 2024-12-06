package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stylrwardrobeapplication.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Profile Details
        binding.tvName.text = "Graham Ogatia"
        binding.tvUsername.text = "@grhmxoxo"

        // Set up RecyclerView
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Example: Setting up a GridLayoutManager
        val layoutManager = GridLayoutManager(this, 3) // 3 columns for grid
        binding.rvPhotos.layoutManager = layoutManager

        // Example: Adding a placeholder adapter (replace with actual data)
        val photos = listOf(
            R.drawable.ic_shirt, R.drawable.ic_top, R.drawable.ic_shoes,
            R.drawable.ic_bottoms, R.drawable.ic_wardrobe, R.drawable.schedule_picture
        )

        // Set up RecyclerView with GridLayoutManager
        binding.rvPhotos.layoutManager = GridLayoutManager(this, 3) // 3 columns
        binding.rvPhotos.adapter = PhotoAdapter(photos)

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
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
                R.id.wardrobe-> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, WardrobeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}


