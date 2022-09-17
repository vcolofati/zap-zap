package com.vcolofati.zapzap.ui.home.fragments.conversation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.data.models.Conversation
import com.vcolofati.zapzap.databinding.ContactsRecyclerItemBinding

class ConversationAdapter(private val glide: RequestManager) :
    RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {
    private var list = mutableListOf<Conversation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItem = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ContactsRecyclerItemBinding>(
            listItem, R.layout.contacts_recycler_item,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversation = list[position]
        with(conversation) {
            holder.binding.textName.text = contact?.name
            holder.binding.textEmail.text = lastMessage
        }

        if (conversation.contact?.imageUrl != null) {
            val uri = Uri.parse(conversation.contact.imageUrl)
            glide.load(uri).into(holder.binding.profileImage)
        } else {
            holder.binding.profileImage.setImageResource(R.drawable.default_image)
        }
    }

    override fun getItemCount() = list.size

    fun bindList(conversation: Conversation) {
        this.list.add(conversation)
        notifyDataSetChanged()
    }

    fun getSelectedConversation(position: Int): Conversation = this.list[position]

    inner class ViewHolder(val binding: ContactsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}