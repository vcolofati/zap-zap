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
import com.vcolofati.zapzap.data.models.Conversation
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.data.models.User
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

    private lateinit var contact: User
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
            saveMessage(userId, contact.uid, message)
            saveMessage(contact.uid, userId, message)
            saveConversation(message)
        }
        textMessage.set("")
    }

    fun getMessages() {
        this.databaseRepository.getMessages(userId!!, contact.uid!!, _messagesList)
    }

    private fun saveMessage(userId: String? = null, contactId: String? = null, message: Message) {
        if (userId != null && contactId != null) {
            this.databaseRepository.create(userId, contactId, message)
        }
    }

    fun bindContact(contact: User?) {
        if (contact != null) {
            this.contact = contact
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
                    // salvando mensagem para o remetente
                    saveMessage(userId, contact.uid, message)
                    // salvando mensagem para o destinatário
                    saveMessage(contact.uid, userId, message)
                    // salvando conversa
                    saveConversation(message)
                }
            }
        }
    }

    private fun saveConversation(message: Message) {
        databaseRepository.saveConversation(Conversation(userId!!, contact.uid!!, message.content!!, contact))
    }
}