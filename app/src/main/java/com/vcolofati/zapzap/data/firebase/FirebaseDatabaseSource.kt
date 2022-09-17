package com.vcolofati.zapzap.data.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.vcolofati.zapzap.data.models.Conversation
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.data.models.User
import javax.inject.Inject

class FirebaseDatabaseSource @Inject constructor(firebaseDatabase: FirebaseDatabase) {

    private val firebaseUserRef = firebaseDatabase.getReference("users")
    private val firebaseMessageRef = firebaseDatabase.getReference("messages")
    private val firebaseConversationRef = firebaseDatabase.getReference("conversations")
    private lateinit var userListener: ValueEventListener
    private lateinit var messageListener: ValueEventListener
    private lateinit var conversationListener: ChildEventListener

    fun create(user: User) {
        user.uid?.let { firebaseUserRef.child(it).setValue(user) }
    }

    fun update(user: User) {
        user.uid?.let { firebaseUserRef.child(it).updateChildren(user.convertClassToMap()) }
    }

    fun getAll(userLiveData: MutableLiveData<List<User>>, email: String) {
        userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = arrayListOf<User>()
                snapshot.children.forEach { dataSnapshot ->
                    if (dataSnapshot.child("email").value as String != email)
                        items.add(
                            User(
                                uid = dataSnapshot.key,
                                name = dataSnapshot.child("name").value as String,
                                email = dataSnapshot.child("email").value as String,
                                imageUrl = dataSnapshot.child("imageUrl").value as String?
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

    fun getMessages(
        userId: String,
        contactId: String,
        messageLiveData: MutableLiveData<List<Message>>
    ) {
        messageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue<Message>()!!
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

    fun saveConversation(conversation: Conversation) {
        firebaseConversationRef.child(conversation.idSender!!)
            .child(conversation.idReceiver!!)
            .setValue(conversation)
    }

    fun getConversations(
        userId: String,
        conversationLiveData: MutableLiveData<Conversation>
    ) {
        conversationListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val conversation = snapshot.getValue<Conversation>()!!
                conversationLiveData.value = conversation
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", error.toString())
            }
        }
        firebaseConversationRef.child(userId).addChildEventListener(conversationListener)
    }

    fun detachConversationListener() {
        firebaseConversationRef.removeEventListener(conversationListener)
    }
}
