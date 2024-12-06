package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stylrwardrobeapplication.databinding.ActivityHomepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var db: FirebaseFirestore

    private lateinit var tvUsername: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageBinding.inflate(layoutInflater)
        tvUsername = binding.tvUsername
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        user = auth.currentUser

        if (user == null) {
            Log.d("AuthDebug", "No user is currently logged in. Redirecting to Login.")
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.d("AuthDebug", "User is logged in with UID: ${user!!.uid}")
            fetchUserData(user!!.uid)
        }


        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // Handle BottomNavigationView item clicks
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
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

    }

    private fun fetchUserData(uid: String) {
        val userRef = db.collection("users").document(uid)

        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document: DocumentSnapshot = task.result!!
                if (document.exists()) {
                    // Assuming you have a field 'username' in your Firestore document
                    val username = document.getString("username")
                    tvUsername.text = username
                } else {
                    tvUsername.text = "User!"
                }
            } else {
                tvUsername.text = "Error fetching user data: ${task.exception?.message}"
            }
        }
    }
}