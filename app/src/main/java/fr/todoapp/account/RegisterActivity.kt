package fr.todoapp.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.todoapp.MainActivity
import fr.todoapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.tasks.await

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

            val email = getEmail.text.toString()
            val password = getPassword.text.toString()

            println("here the email and password : $email, $password")
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("Register done", "Register successfully worked")
                        val user = auth.currentUser
                        val userId = user?.uid

                        // setting parameters of user to add to firebase later
                        val userData = hashMapOf(
                            "email" to email,
                            "password" to password,
                            "userId" to userId
                        )
                        // if userId exist, add to firebase the user data
                        if (userId != null) {
                            db.collection("users")
                                .document("$userId")
                                .set(userData)
                                .addOnSuccessListener {
                                    Log.d("Success Register", "DocumentSnapshot written with ID")
                                }
                                .addOnFailureListener {
                                    Log.d("Register Failed", "Error")
                                }
                        }


                        updateUI(user)

                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Log.d("Register failed", "Register failed")
                        Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }

            } catch (e: Exception) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}