package fr.todoapp

import android.app.Activity
import android.app.Dialog

class CustomPopup: Dialog() {

    fun CustomPopup(activity: Activity) {

        super(activity, R.style.Theme_AppCompat_DayNight_Dialog)

    }
}