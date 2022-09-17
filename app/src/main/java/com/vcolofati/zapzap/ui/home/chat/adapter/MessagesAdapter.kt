package com.vcolofati.zapzap.ui.home.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.vcolofati.zapzap.data.models.Message
import com.vcolofati.zapzap.databinding.RecipientRecyclerItemBinding
import com.vcolofati.zapzap.databinding.SenderRecyclerItemBinding
import com.vcolofati.zapzap.ui.configuration.RECIPIENT_TYPE
import com.vcolofati.zapzap.ui.configuration.SENDER_TYPE
import com.vcolofati.zapzap.utils.convertToDate

class MessagesAdapter(private val userId: String?, private val glideInstance: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = emptyList<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SENDER_TYPE -> {
                val binding = SenderRecyclerItemBinding.inflate(layoutInflater, parent, false)
                SenderViewHolder(binding)
            }
            RECIPIENT_TYPE -> {
                val binding = RecipientRecyclerItemBinding.inflate(layoutInflater, parent, false)
                RecipientViewHolder(binding)
            }
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
        return if (this.userId == message.userId) SENDER_TYPE else RECIPIENT_TYPE
    }

    fun bindList(list: List<Message>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class SenderViewHolder(val binding: SenderRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val message = list[position]
            if (message.image != null) {
                binding.imageAnexed.visibility = View.VISIBLE
                binding.textMessage.visibility = View.GONE
                glideInstance.load(message.image).into(binding.imageAnexed)
            } else {
                binding.textTimestamp.text = message.timestampLong?.convertToDate()
                binding.textMessage.text = message.content
            }
        }
    }

    inner class RecipientViewHolder(val binding: RecipientRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val message = list[position]
            if (message.image != null) {
                binding.imageAnexedOther.visibility = View.VISIBLE
                binding.textMessageOther.visibility = View.GONE
                glideInstance.load(message.image).into(binding.imageAnexedOther)
            } else {
                binding.textTimestampOther.text = message.timestampLong?.convertToDate()
                binding.textMessageOther.text = message.content
            }
        }
    }
}