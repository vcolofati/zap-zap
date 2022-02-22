package com.vcolofati.zapzap.data.repositories

import com.vcolofati.zapzap.data.firebase.FirebaseDatabaseSource
import com.vcolofati.zapzap.data.models.User

class DatabaseRepository {

    private val firebaseDatabase: FirebaseDatabaseSource = FirebaseDatabaseSource()

    fun create(user: User) = firebaseDatabase.create(user)
    fun update(user: User) = firebaseDatabase.update(user)
}