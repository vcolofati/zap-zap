package com.vcolofati.zapzap.data.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.vcolofati.zapzap.data.models.User
import javax.inject.Inject

class FirebaseDatabaseSource @Inject constructor(private val firebaseUserRef: DatabaseReference) {

    private lateinit var userListener: ValueEventListener

    fun create(user: User) {
        user.uid?.let { firebaseUserRef.child(it).setValue(user) }
    }

    fun update(user: User) {
        user.uid?.let { firebaseUserRef.child(it).updateChildren(user.convertClassToMap()) }
    }

    fun getAll(userLiveData: MutableLiveData<List<User>>, email: String) {
        userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<User>()!!
                }.filter { user ->
                    user.email != email
                }
                userLiveData.postValue(items)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", error.toString())
            }
        }
        firebaseUserRef.orderByChild("name").addValueEventListener(userListener)
    }

    fun detachUserListener() {
        firebaseUserRef.removeEventListener(userListener)
    }
}
