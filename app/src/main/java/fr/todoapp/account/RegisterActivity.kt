package fr.todoapp.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.todoapp.MainActivity
import fr.todoapp.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val auth = Firebase.auth

        // get email and password from user input
        val getEmail = findViewById<EditText>(R.id.username_register)
        val getPassword = findViewById<EditText>(R.id.password_register)

        // if register button is clicked, create account and add to firestore DB
        val registerBtn = findViewById<Button>(R.id.signIn).setOnClickListener {

            // set user input to variable
            val email = getEmail.text.toString()
            val password = getPassword.text.toString()

            // try to create the account with email and password
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user?.uid

                        // setting parameters of user to add to firebase later
                        val userData = hashMapOf(
                            "email" to email,
                            "password" to password,
                            "userId" to userId
                        )
                        // add to firebase user's data
                        db.collection("users")
                            .document("$userId")
                            .set(userData)
                            .addOnSuccessListener {
                                Log.d("Success Register", "DocumentSnapshot written with ID")
                            }
                            .addOnFailureListener {
                                Log.d("Register Failed", "Error")

                            }

                        updateUI(user)

                        // if register was successful, goto MainActivity
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                    // if register failed
                    else {
                        Log.d("Register failed", "Register failed")
                        Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
            }

            // if there is empty field, catch the error
            catch (e: Exception) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }
}