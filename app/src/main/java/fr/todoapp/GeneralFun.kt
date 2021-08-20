package fr.todoapp

import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.todoapp.recyclerViewAdapters.TaskAdapter

object GeneralFun : AppCompatActivity() {


    // fun to save locally data of tasks with shared preferences
    fun saveDataSharedPreferencesList(
        key: String,
        myList: ArrayList<Task>,
        name: String,
        sharedPreferences: SharedPreferences = getSharedPreferences(
            name, MODE_PRIVATE
        )
    ) {

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(myList)

        if (!sharedPreferences.getString(key, "")?.equals(json)!!) {
            editor.putString(key, json)
            editor.apply()
        }
    }


    // fun to save locally data of tasks with shared preferences
    fun saveDataSharedPreferencesFirebase(
        key: String,
        myList: Unit,
        name: String,
        sharedPreferences: SharedPreferences = getSharedPreferences(
            name, MODE_PRIVATE
        )
    ) {

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(myList)

        if (!sharedPreferences.getString(key, "")?.equals(json)!!) {
            editor.putString(key, json)
            editor.apply()
        }
    }



}