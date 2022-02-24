package com.vcolofati.zapzap.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.vcolofati.zapzap.ui.auth.LoginActivity
import com.vcolofati.zapzap.ui.configuration.SettingsActivity
import com.vcolofati.zapzap.ui.home.HomeActivity
import com.vcolofati.zapzap.ui.home.chat.ChatActivity

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

fun Context.startChatActivity() =
    Intent(this, ChatActivity::class.java).also {
        startActivity(it)
    }