package com.vcolofati.zapzap.ui.home.fragments.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.databinding.ContactsFragmentBinding
import com.vcolofati.zapzap.ui.home.fragments.contact.adapter.ContactsAdapter

class ContactsFragment : Fragment() {

    private val contactsList: List<User> = emptyList()
    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ContactsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.contacts_fragment, container, false)
        val view = binding.root
        // Adapter
        val adapter = ContactsAdapter(Glide.with(requireActivity()))
        // Recycler View
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.contactsRecycler.layoutManager = layoutManager
        binding.contactsRecycler.setHasFixedSize(true)
        binding.contactsRecycler.adapter = adapter
        setObservers(adapter)
        this.viewModel.fetchUserList()
        return view
    }

    private fun setObservers(adapter: ContactsAdapter) {
        this.viewModel.list.observe(viewLifecycleOwner) { list ->
            adapter.bindList(list)
        }
    }
}