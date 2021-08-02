package fr.todoapp.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.todoapp.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.lang.StringBuilder

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        radioButtonDarkTheme.setText(R.string.dark_theme)





        radioButtonLightTheme.setText(R.string.light_theme)



        radioButtonDefaultTheme.setText(R.string.default_system_theme)


    }
}