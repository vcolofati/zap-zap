package com.vcolofati.zapzap.ui.home.fragments.conversation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.databinding.ContactsRecyclerItemBinding
import com.vcolofati.zapzap.databinding.ConversationFragmentBinding

class ConversationAdapter(private val glide: RequestManager): RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {
    private var list: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItem = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ContactsRecyclerItemBinding>(listItem, R.layout.contacts_recycler_item,
        parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.binding.textName.text = user.name
        holder.binding.textEmail.text = user.email

        if (user.imageUrl != null) {
            val uri = Uri.parse(user.imageUrl)
            glide.load(uri).into(holder.binding.profileImage)
        } else {
            holder.binding.profileImage.setImageResource(R.drawable.default_image)
        }
    }

    override fun getItemCount() = list.size

    fun bindList(list: List<User>) {
        this.list = list
    }

    fun getSelectedUser(position: Int): User = this.list[position]

    inner class ViewHolder(val binding: ContactsRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)
}