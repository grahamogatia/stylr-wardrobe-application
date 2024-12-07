package com.example.stylrwardrobeapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.stylrwardrobeapplication.databinding.ActivityCreateOutfitBinding
import com.google.common.io.Files.getFileExtension
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CreateOutfitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateOutfitBinding

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var categorySpinner: Spinner

    private var imageUri: Uri? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Inflate the layout using View Binding
        binding = ActivityCreateOutfitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        setupCategorySpinner()
        registerClickEvents()
    }

    private fun initVars() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        storageRef = FirebaseStorage.getInstance().reference.child("users/$userId/Images")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupCategorySpinner() {
        categorySpinner = binding.categorySpinner
        val categories = listOf("Top", "Bottom", "Shoes", "OOTD")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    private fun registerClickEvents() {
        // Bottom navigation menu
        binding.bottomNavMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    // Navigate to ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.home -> {
                    // Navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.wardrobe -> {
                    // Navigate to WardrobeActivity
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

        // Uploading clothes
        progressBar = binding.progressBar

        binding.btnUpload.setOnClickListener {
            uploadImage()
        }
        binding.ivClothesPreview.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.ivClothesPreview.setImageURI(it)
    }

    private fun uploadImage() {

        progressBar.visibility = View.VISIBLE

        if (imageUri == null) {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate a unique filename based on timestamp
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val fileName = "${System.currentTimeMillis()}.jpg"
        val fileRef = storageRef.child(fileName)

        // Upload image to Firebase Storage
        val uploadTask = fileRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            // Get the download URL
            progressBar.visibility = View.GONE

            fileRef.downloadUrl.addOnSuccessListener { uri ->
                saveImageDetailsToFirestore(uri.toString(), fileName)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to get download URL: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            progressBar.visibility = View.GONE
            val fileSize = imageUri?.let { contentResolver.openInputStream(it)?.available() }
            Log.d("FileDetails", "File size: $fileSize bytes")
            Log.d("FirebaseStorage", "Uploading to path: ${fileRef.path}")
            Toast.makeText(this, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageDetailsToFirestore(imageUrl: String, fileName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val clotheType = categorySpinner.selectedItem.toString()
        val imageDetails = hashMapOf(
            "imageUrl" to imageUrl,
            "fileName" to fileName,
            "timestamp" to System.currentTimeMillis(),
            "clotheType" to clotheType
        )

        firebaseFirestore.collection("users").document(userId).collection(clotheType)
            .add(imageDetails)
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot added with ID: ${it.id}")
                Toast.makeText(this, "Image metadata saved to Firestore!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save metadata: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
