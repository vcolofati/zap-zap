package com.vcolofati.zapzap.ui.home.fragments.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.databinding.ContactsFragmentBinding
import com.vcolofati.zapzap.ui.home.fragments.contact.adapter.ContactsAdapter
import com.vcolofati.zapzap.utils.RecyclerItemClickListener
import com.vcolofati.zapzap.utils.startChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

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
        binding.contactsRecycler.addOnItemTouchListener(object : RecyclerItemClickListener(activity,
        binding.contactsRecycler,
            object : OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    activity?.startChatActivity(adapter.getSelectedUser(position))
                }

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TODO("Not yet implemented")
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    TODO("Not yet implemented")
                }

            }) {
        })
        return view
    }

    private fun setObservers(adapter: ContactsAdapter) {
        this.viewModel.list.observe(viewLifecycleOwner) { list ->
            adapter.bindList(list)
        }
    }

    override fun onDestroyView() {
        this.viewModel.detachUserListener()
        super.onDestroyView()
    }
}