package com.vcolofati.zapzap.utils

import android.content.Context
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText

fun Context.toast(message: String) {
    makeText(this, message, LENGTH_SHORT).show()
}