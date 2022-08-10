package com.vcolofati.zapzap.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.ui.auth.LoginActivity
import com.vcolofati.zapzap.ui.configuration.CONTACT_KEY
import com.vcolofati.zapzap.ui.configuration.SettingsActivity
import com.vcolofati.zapzap.ui.home.HomeActivity
import com.vcolofati.zapzap.ui.home.chat.ChatActivity
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("HH:mm")

fun Context.toast(message: String) {
    makeText(this, message, LENGTH_SHORT).show()
}

fun Context.startHomeActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startConfigurationActivity() =
    Intent(this, SettingsActivity::class.java).also {
        startActivity(it)
    }

fun Context.startChatActivity(user: User) =
    Intent(this, ChatActivity::class.java).also {
        it.putExtra(CONTACT_KEY, user)
        startActivity(it)
    }

fun Long.convertToDate(): String =
    Date(this).run {
        sdf.format(this)
    }
