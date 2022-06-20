package com.vcolofati.zapzap.data.models

import com.google.firebase.database.Exclude

class Conversation(@get:Exclude val idSender: String, @get:Exclude val idReceiver: String, val lastMessage: String, val contact: User)