package com.vcolofati.zapzap.ui.home.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.databinding.RecipientRecyclerItemBinding
import com.vcolofati.zapzap.databinding.SenderRecyclerItemBinding
import com.vcolofati.zapzap.ui.configuration.RECIPIENT_TYPE
import com.vcolofati.zapzap.ui.configuration.SENDER_TYPE

class MessagesAdapter(private val userId: String?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = emptyList<Message?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SENDER_TYPE -> SenderViewHolder(parent)
            RECIPIENT_TYPE -> RecipientViewHolder(parent)
            else -> throw IllegalArgumentException("Invalid viewtype")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SenderViewHolder -> holder.bind(position)
            is RecipientViewHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int = this.list.size

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        if (this.userId == message?.userId) {
            return SENDER_TYPE
        }
        return RECIPIENT_TYPE
    }

    fun bindList(list: List<Message?>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class SenderViewHolder(val binding: SenderRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            SenderRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        fun bind(position: Int) {
            binding.chatMessage.text = list[position]?.content
        }
    }

    inner class RecipientViewHolder(val binding: RecipientRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            RecipientRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        fun bind(position: Int) {
            binding.chatMessage.text = list[position]?.content
        }
    }
}