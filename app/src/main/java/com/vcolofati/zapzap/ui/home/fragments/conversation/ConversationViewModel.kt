package com.vcolofati.zapzap.ui.home.fragments.conversation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcolofati.zapzap.data.models.Conversation
import com.vcolofati.zapzap.data.repositories.AuthRepository
import com.vcolofati.zapzap.data.repositories.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _conversationList = MutableLiveData<Conversation>()
    val conversationList = _conversationList

    val user by lazy {
        authRepository.currentUser()!!
    }

    fun fetchConversationList() {
        this.databaseRepository.getConversations(user.uid, _conversationList)
    }

    fun detachConversationListener() {
        this.databaseRepository.detachConversationListener()
    }


}