package fr.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.todoapp.account.LoginActivity
import fr.todoapp.tasklist.TaskListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        gotoTaskButton.setOnClickListener{
            startActivity(Intent (applicationContext, TaskListActivity::class.java))
        }

        logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Successfully Log out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
}