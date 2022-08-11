package com.vcolofati.zapzap.data.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.ServerValue
import java.io.Serializable
import java.sql.Timestamp

class Message : Serializable {
    var userId: String? = null
    var content: String? = null
    var image: String? = null
    @get:Exclude var timestampLong: Long? = null
        private set

    constructor()
    constructor(userId: String?, content: String?, image: String?) {
        this.userId = userId
        this.content = content
        this.image = image
    }

    fun getTimestamp(): Map<String, String> {
        return ServerValue.TIMESTAMP
    }

    fun setTimestamp(timestamp: Long?) {
        this.timestampLong = timestamp
    }
}