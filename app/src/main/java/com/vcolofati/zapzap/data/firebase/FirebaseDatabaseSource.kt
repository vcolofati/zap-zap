package com.vcolofati.zapzap.data.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.data.models.User
import javax.inject.Inject

class FirebaseDatabaseSource @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    private val firebaseUserRef = firebaseDatabase.getReference("users")
    private val firebaseMessageRef = firebaseDatabase.getReference("messages")
    private lateinit var userListener: ValueEventListener
    private lateinit var messageListener: ValueEventListener

    fun create(user: User) {
        user.uid?.let { firebaseUserRef.child(it).setValue(user) }
    }

    fun update(user: User) {
        user.uid?.let { firebaseUserRef.child(it).updateChildren(user.convertClassToMap()) }
    }

    fun getAll(userLiveData: MutableLiveData<List<User>>, email: String) {
        userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: MutableList<User> = arrayListOf()
                snapshot.children.forEach { dataSnapshot ->
                    if (dataSnapshot.child("email").value as String != email)
                        items.add(
                            User(
                                dataSnapshot.key,
                                dataSnapshot.child("name").value as String,
                                dataSnapshot.child("email").value as String, null,
                                dataSnapshot.child("imageUrl").value as String
                            )
                        )
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

    fun create(userId: String, contactId: String, message: Message) {
        firebaseMessageRef.child(userId)
            .child(contactId)
            .push()
            .setValue(message)
    }

    fun getMessages(userId: String, contactId: String, messageLiveData: MutableLiveData<List<Message?>>) {
        messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<Message>()
                }
                messageLiveData.postValue(items)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", error.toString())
            }
        }
        firebaseMessageRef.child(userId).child(contactId).addValueEventListener(messageListener)
    }

    fun detachMessageListener() {
        firebaseMessageRef.removeEventListener(messageListener)
    }
}
