package com.example.stylrwardrobeapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stylrwardrobeapplication.databinding.ActivityWardrobeBinding

class WardrobeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWardrobeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWardrobeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners
        setupButtonListeners()
        setupRecyclerView()
    }

    private fun setupButtonListeners() {
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.add-> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, CreateOutfitActivity::class.java)
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

        binding.btnOutfit.setOnClickListener {
            Toast.makeText(this, "Outfit button clicked!", Toast.LENGTH_SHORT).show()
            // Add navigation or action logic here
        }

        binding.btnTop.setOnClickListener {
            Toast.makeText(this, "Top button clicked!", Toast.LENGTH_SHORT).show()
            // Add navigation or action logic here
        }

        binding.imageButton15.setOnClickListener {
            Toast.makeText(this, "Bottoms button clicked!", Toast.LENGTH_SHORT).show()
            // Add navigation or action logic here
        }

        binding.imageButton16.setOnClickListener {
            Toast.makeText(this, "Shoes button clicked!", Toast.LENGTH_SHORT).show()
            // Add navigation or action logic here
        }
    }

    private fun setupRecyclerView() {
        // Example image data
        val sampleData = listOf(
            R.drawable.ic_bottoms,
            R.drawable.ic_top,
            R.drawable.ic_shoes,
            R.drawable.ic_bottoms
        )

        // Set up GridLayoutManager with 2 columns
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Set up adapter
        binding.recyclerView.adapter = WardrobeAdapter(sampleData)
    }

}

