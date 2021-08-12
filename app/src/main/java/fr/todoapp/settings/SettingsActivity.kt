package fr.todoapp.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import fr.todoapp.MainActivity
import fr.todoapp.R
import fr.todoapp.Task
import fr.todoapp.recyclerViewAdapters.TaskAdapter
import fr.todoapp.taskList.TaskListActivity
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.goBackBtn
import kotlinx.android.synthetic.main.activity_task_list.*
import java.io.Serializable

class SettingsActivity : AppCompatActivity() {
    private val taskList = ArrayList<Task>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //val taskAdapter = taskAdap

        loadDataRadioBtn()

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

        // collect data from firebase
         buttonGetDataFb.setOnClickListener {

             val intent = Intent(applicationContext, TaskListActivity::class.java)
             intent.putExtra("getDataFromDatabase",123)
             startActivity(intent)



             //saveDataSharedPreferences("fbData", taskListActivity.myList
             //    , "firebase")

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