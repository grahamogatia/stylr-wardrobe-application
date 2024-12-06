package com.example.stylrwardrobeapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stylrwardrobeapplication.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Signup : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var editTextUsername: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextConfirmPassword: TextInputEditText

    private lateinit var progressBar: ProgressBar
    private lateinit var btnSignup: Button

    //private lateinit var dbHelper: UserDatabaseHelper
    private var db = Firebase.firestore

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        auth = Firebase.auth

        editTextUsername = viewBinding.usernameSignup
        editTextEmail = viewBinding.emailSignup
        editTextPassword = viewBinding.passwordSignup
        editTextConfirmPassword = viewBinding.confirmPasswordSignup

        progressBar = viewBinding.progressBar
        btnSignup = viewBinding.btnSignup

        btnSignup.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            val username = editTextUsername.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Implement signup logic here

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = ProgressBar.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(
                            this,
                            "Account created.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val userMap = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "password" to password)
                        addToFirestore(userMap)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this,
                            "Authentication failed. Email: $email, Password: $password",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    fun addToFirestore(userMap: HashMap<String, String>) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document(userId).set(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added to Firestore!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { Toast.makeText(this, "Failed (Firestore)!", Toast.LENGTH_SHORT).show() }
    }
}