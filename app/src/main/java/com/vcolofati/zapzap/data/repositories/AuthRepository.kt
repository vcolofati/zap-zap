package com.vcolofati.zapzap.data.repositories

import com.vcolofati.zapzap.data.firebase.FirebaseSource


class AuthRepository {

    private val firebase: FirebaseSource = FirebaseSource()

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}