package fr.todoapp.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import fr.todoapp.MainActivity
import fr.todoapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadDataRadioBtn()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        goBackBtn.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }


        // switch theme buttons

        // switch from dark to light theme
        radioButtonLightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }

        // switch from light to dark theme
        radioButtonDarkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        // theme by default based on the system
        radioButtonDefaultTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun onStop() {
        super.onStop()

        saveDataRadioBtn()

    }


    private fun saveDataRadioBtn() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "radioBtnChecked", MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()


        val localRadioGroup: RadioGroup = findViewById(R.id.radioGroupTheme)
        editor.putInt(
            "radioGrpInt", localRadioGroup.indexOfChild(
                findViewById(
                    localRadioGroup.checkedRadioButtonId
                )
            )
        )
        editor.apply()

    }

    private fun loadDataRadioBtn() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "radioBtnChecked", MODE_PRIVATE
        )
        val i: Int = sharedPreferences.getInt("radioGrpInt", -1)
        if (i >= 0) {
            ((findViewById<View>(R.id.radioGroupTheme) as RadioGroup).getChildAt(i) as RadioButton)
                .isChecked = true
        }
    }
}