package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stylrwardrobeapplication.databinding.ActivityCreateOutfitBinding
import com.example.stylrwardrobeapplication.databinding.ActivityHomepageBinding


class CreateOutfitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOutfitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityCreateOutfitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listeners for the buttons
        binding.btnAddTop.setOnClickListener {
            Toast.makeText(this, "Add Top clicked!", Toast.LENGTH_SHORT).show()
            // Handle navigation or functionality here
        }

        binding.btnAddBottom.setOnClickListener {
            Toast.makeText(this, "Add Bottom clicked!", Toast.LENGTH_SHORT).show()
            // Handle navigation or functionality here
        }

        binding.btnAddShoes.setOnClickListener {
            Toast.makeText(this, "Add Shoes clicked!", Toast.LENGTH_SHORT).show()
            // Handle navigation or functionality here
        }


        // Handle BottomNavigationView item clicks
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.home-> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
