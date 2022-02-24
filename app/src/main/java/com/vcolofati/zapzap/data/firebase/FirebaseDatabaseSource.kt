package com.vcolofati.zapzap.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.vcolofati.zapzap.data.models.User
import javax.inject.Inject

class FirebaseDatabaseSource @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    private var fireBaseRef = firebaseDatabase.getReference("users")

    fun create(user: User) {
        user.uid?.let { fireBaseRef.child(it).setValue(user) }
    }

    fun update(user: User) {
        user.uid?.let { fireBaseRef.child(it).updateChildren(user.convertClassToMap()) }
    }

    fun getAll(mtld: MutableLiveData<List<User>>, email: String) {
        // preciso remover o listener quanto a tela de contactos é fechada
        fireBaseRef.orderByChild("name").addValueEventListener(object : ValueEventListener {
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
