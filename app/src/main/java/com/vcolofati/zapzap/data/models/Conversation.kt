package com.vcolofati.zapzap.data.models

import com.google.firebase.database.Exclude

class Conversation(
    @get:Exclude val idSender: String? = null,
    @get:Exclude val idReceiver: String? = null,
    val lastMessage: String? = null,
    val contact: User? = null
)