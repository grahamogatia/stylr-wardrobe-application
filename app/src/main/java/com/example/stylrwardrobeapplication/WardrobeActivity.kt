package com.example.stylrwardrobeapplication


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stylrwardrobeapplication.databinding.ActivityWardrobeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class WardrobeActivity : AppCompatActivity() {

    companion object {
        private const val TOP = "Top"
        private const val BOTTOM = "Bottom"
        private const val SHOES = "Shoes"
        private const val OUTFIT = "OOTD"
    }

    private lateinit var binding: ActivityWardrobeBinding

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: WardrobeAdapter

    private var imageList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWardrobeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up button click listeners
        setupButtonListeners()

        // Initialize variables
        initVars()
        setDefaultButton()
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
            clearRecyclerViewData()
            getOutfitImages(TOP)
            getOutfitImages(BOTTOM)
            getOutfitImages(SHOES)
            highlightButton(binding.btnOutfit)
            Toast.makeText(this, "Outfit button clicked!", Toast.LENGTH_SHORT).show()
            getOutfitImages(OUTFIT)
        }

        binding.btnTop.setOnClickListener {
            clearRecyclerViewData()
            highlightButton(binding.btnTop)
            Toast.makeText(this, "Top button clicked!", Toast.LENGTH_SHORT).show()
            getOutfitImages(TOP)
        }

        binding.btnBottoms.setOnClickListener {            clearRecyclerViewData()
            clearRecyclerViewData()
            highlightButton(binding.btnBottoms)
            Toast.makeText(this, "Bottoms button clicked!", Toast.LENGTH_SHORT).show()
            getOutfitImages(BOTTOM)
        }

        binding.btnShoes.setOnClickListener {
            clearRecyclerViewData()
            highlightButton(binding.btnShoes)
            Toast.makeText(this, "Shoes button clicked!", Toast.LENGTH_SHORT).show()
            getOutfitImages(SHOES)
        }
    }

    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WardrobeAdapter(imageList)
        binding.recyclerView.adapter = adapter
    }

    private fun getOutfitImages(type: String) {
        // Get the current user's UID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Query Firestore to get outfits specific to the user
        firebaseFirestore.collection("users")
            .document(userId) // Get the user's document using their UID
            .collection(type) // Access the "outfits" subcollection
            .get() // Get all documents in the "outfits" subcollection
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Toast.makeText(this, "No outfits found for this user.", Toast.LENGTH_SHORT).show()
                } else {
                    // Iterate through each outfit document and extract the imageUrl
                    for (document in result) {
                        val imageUrl = document.getString("imageUrl")
                        if (imageUrl != null) {
                            imageList.add(imageUrl)
                        }
                    }
                    // Notify the adapter that data has been updated
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting documents: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearRecyclerViewData() {
        // Clear the data from the list
        imageList.clear()

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged()
    }

    private fun highlightButton(button: ImageButton) {
        resetButtonBackgrounds()
        button.setBackgroundResource(R.drawable.button_highlighted)
    }

    private fun resetButtonBackgrounds() {
        // Reset the background of all buttons (set it to default)
        binding.btnOutfit.setBackgroundResource(R.drawable.button_default)
        binding.btnTop.setBackgroundResource(R.drawable.button_default)
        binding.btnBottoms.setBackgroundResource(R.drawable.button_default)
        binding.btnShoes.setBackgroundResource(R.drawable.button_default)
    }

    private fun setDefaultButton() {
        clearRecyclerViewData()
        highlightButton(binding.btnOutfit) // Highlight the btnOutfit
        getOutfitImages(TOP)              // Fetch TOP images
        getOutfitImages(BOTTOM)           // Fetch BOTTOM images
        getOutfitImages(SHOES)            // Fetch SHOES images
        Toast.makeText(this, "Default Outfit display loaded.", Toast.LENGTH_SHORT).show()
    }

}

