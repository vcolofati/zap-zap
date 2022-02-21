package com.vcolofati.zapzap.data.repositories

import android.net.Uri
import com.vcolofati.zapzap.data.firebase.FirebaseAuthSource


class AuthRepository {

    private val firebaseAuth: FirebaseAuthSource = FirebaseAuthSource()

    fun login(email: String, password: String) = firebaseAuth.login(email, password)

    fun register(email: String, password: String) = firebaseAuth.register(email, password)

    fun currentUser() = firebaseAuth.currentUser()

    fun logout() = firebaseAuth.logout()

    suspend fun updateUserProfile(uri: Uri?) = firebaseAuth.updateUserProfile(uri)
}