package com.vcolofati.zapzap.utils

interface AuthListener {
    fun onStarted()
    fun onSucess()
    fun onFailure(message: String)
}