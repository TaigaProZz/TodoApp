package fr.todoapp.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.todoapp.MainActivity
import fr.todoapp.R
import fr.todoapp.taskList.TaskListActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val getEmail = findViewById<EditText>(R.id.username_login)
        val getPassword = findViewById<EditText>(R.id.password_login)

        loginBtn.setOnClickListener {
            val email = getEmail.text.toString()
            val password = getPassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()

                    } else{
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()

                    }
                }


        }

        gotoRegisterActivity.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }


    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null){
            startActivity(Intent(applicationContext, TaskListActivity::class.java))
        }
    }


}
