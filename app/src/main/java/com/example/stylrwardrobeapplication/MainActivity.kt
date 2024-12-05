package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stylrwardrobeapplication.databinding.ActivityHomepageBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                else -> false
            }
        }
    }
}




/*package com.example.stylrwardrobeapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylrwardrobeapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var db: FirebaseFirestore

    private lateinit var textView: TextView
    private lateinit var btnLogout: Button





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_homepage)

        /*viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        btnLogout = viewBinding.btnLogout
        textView = viewBinding.userDetails
        user = auth.currentUser!!

        if (user == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            fetchUserData(user.uid)
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
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
                    textView.text = "User: $username"
                } else {
                    textView.text = "No user data found."
                }
            } else {
                textView.text = "Error fetching user data: ${task.exception?.message}"
            }
        }
    }
}
*/
        */