package com.vcolofati.zapzap.data.repositories

import androidx.lifecycle.MutableLiveData
import com.vcolofati.zapzap.data.firebase.FirebaseDatabaseSource
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.data.models.User
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabaseSource) {

    fun create(user: User) = firebaseDatabase.create(user)
    fun update(user: User) = firebaseDatabase.update(user)
    fun getUsers(userLiveData: MutableLiveData<List<User>>, email: String) =
            firebaseDatabase.getAll(userLiveData, email)
    fun detachUserListener() = firebaseDatabase.detachUserListener()
    fun create(userId: String, contactId: String, message: Message) =
        firebaseDatabase.create(userId, contactId,message)

    fun getMessages(userId: String, contactId: String, messageLiveData: MutableLiveData<List<Message?>>) =
        firebaseDatabase.getMessages(userId, contactId, messageLiveData)
    fun detachMessageListener() = firebaseDatabase.detachMessageListener()
}