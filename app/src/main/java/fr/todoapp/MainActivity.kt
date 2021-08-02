package fr.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.todoapp.account.LoginActivity
import fr.todoapp.settings.SettingsActivity
import fr.todoapp.taskList.TaskListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // go to task list activity button
        gotoTaskButton.setOnClickListener {
            startActivity(Intent(applicationContext, TaskListActivity::class.java))
        }

        // log out button
        logout.setOnClickListener {

            Firebase.auth.signOut()
            Toast.makeText(this, "Successfully Log out", Toast.LENGTH_SHORT).show()
            // and goto login activity
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }


        // switch theme buttons

        // switch from dark to light theme
        btnSwitchThemeToLight.setOnClickListener {

            when (AppCompatDelegate.MODE_NIGHT_YES) {
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        // switch from light to dark theme
        btnSwitchThemeToDark.setOnClickListener {

            when (AppCompatDelegate.MODE_NIGHT_NO) {
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        settingsBtn.setOnClickListener {

            finish()
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }

    }
}
