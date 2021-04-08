package com.example.hackaflow.extensions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()

}

fun Fragment.toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}







