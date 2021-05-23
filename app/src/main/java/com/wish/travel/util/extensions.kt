package com.wish.travel.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun List<String>.toStringWithoutBrackets() = toString().replace("[", "").replace("]", "")

fun Context.toast(@StringRes stringRes: Int) {
    Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
