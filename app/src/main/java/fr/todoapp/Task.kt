package fr.todoapp

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.lang.System.out

data class Task(val taskName: String = "", val isChecked: Boolean = true) :


    Serializable, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}

