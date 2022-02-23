package com.vcolofati.zapzap.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
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

    fun update(user: User) {
        user.uid?.let { fireBaseRef?.child(it)?.updateChildren(user.convertClassToMap()) }
    }

    fun getAll(mtld: MutableLiveData<List<User>>, email: String) {
        fireBaseRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<User>()!!
                }.filter {
                    it.email != email
                }
                mtld.postValue(items)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
