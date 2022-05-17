package com.vcolofati.zapzap.ui.home.chat

import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.DatabaseRepository
import com.vcolofati.zapzap.data.repositories.StorageRepository
import com.vcolofati.zapzap.ui.home.chat.adapter.MessagesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {

    private lateinit var contactId: String
    private val userId by lazy { authRepository.currentUser()?.uid }
    var textMessage = ObservableField<String?>()

    private val _messagesList: MutableLiveData<List<Message?>> = MutableLiveData()
    val messagesList: LiveData<List<Message?>> = _messagesList

    fun sendMessage(view: View) {
        if (textMessage.get().isNullOrEmpty()) {
            Toast.makeText(view.context, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT)
                .show()
        } else {
            val message = Message(userId, textMessage.get(), null)
            saveMessage(userId, contactId, message)
            saveMessage(contactId, userId, message)
        }
        textMessage.set("")
    }

    fun getMessages() {
        this.databaseRepository.getMessages(userId!!, contactId, _messagesList)
    }

    private fun saveMessage(userId: String? = null, contactId: String? = null, message: Message) {
        if (userId != null && contactId != null) {
            this.databaseRepository.create(userId, contactId, message)
        }
    }

    fun bindContactId(uid: String?) {
        if (uid != null) {
            this.contactId = uid
        }
    }

    fun createAdapter(glideInstance: RequestManager): MessagesAdapter {
        return MessagesAdapter(userId, glideInstance)
    }

    fun detachMessageListener() = this.databaseRepository.detachMessageListener()

    fun save(takenImage: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userId != null) {
                val uri = storageRepository.saveImageToStorage(takenImage, userId!!)
                withContext(Dispatchers.Main) {
                    val message = Message(userId, "image.jpeg", uri.toString())
                    saveMessage(userId, contactId, message)
                    saveMessage(contactId, userId, message)
                }
            }
        }
    }
}