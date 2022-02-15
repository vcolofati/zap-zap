package com.vcolofati.zapzap.data.firebase;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase;
import com.vcolofati.zapzap.data.models.User

class FirebaseDatabaseSource {

    companion object {
        private var firebaseDatabase: FirebaseDatabase? = null
            get() {
                if (field == null) {
                    firebaseDatabase = Firebase.database
                }
                return field
            }
    }

    private var fireBaseRef = firebaseDatabase?.getReference("users")

    fun create(user: User) {
        user.uid?.let { fireBaseRef?.child(it)?.setValue(user) }
    }
}
