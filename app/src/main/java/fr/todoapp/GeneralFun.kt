package fr.todoapp

import android.content.SharedPreferences
import android.util.Log
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
    fun saveDataSharedPreferences(
        key: String,
        myList: ArrayList<Task>,
        test: String,
        sharedPreferences: SharedPreferences = getSharedPreferences(
            test, MODE_PRIVATE
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


    //collect task data from firebase
   // fun getTaskFromFirebase(
   //     myList: ArrayList<Task>,
//
   //     ) {
//
   //     val db: FirebaseFirestore = Firebase.firestore
   //     val userId: String? = Firebase.auth.currentUser?.uid
   //     val taskAdapter = TaskAdapter(myList)
//
   //     db.collection("users").document("$userId")
   //         .collection("tasks")
   //         .get()
   //         .addOnSuccessListener {
   //             for (document in it) {
//
   //                 // convert the firebase data to the Task object
   //                 val setDocToTask = document.toObject<Task>()
//
   //                 // collect doc id who equals to the task name
   //                 val name = document.id
//
   //                 // initialize the task object with the task name collected
   //                 val task = Task(name)
   //          //       println("ma task = $task")
//
   //                 // check if the task already exist
   //                 val nameExist = task in myList
   //                 if (nameExist) {
   //                     break
   //
   //                 } else {
   //                     // add the collected task to the list and recycler view
 ////                     taskAdapter.addTodo(task)
   //                   //  println("ma task = $task")
   //                      myList.add(task)
   //                    // println("ma liste fun fdun = $myList")
   //                 }
   //             }
   //         }
   //         .addOnFailureListener {
   //             Log.d("Fail to get", "Fail")
   //         }
//
   //     println("ma liste fun fdun = $myList")
//
   // }

}