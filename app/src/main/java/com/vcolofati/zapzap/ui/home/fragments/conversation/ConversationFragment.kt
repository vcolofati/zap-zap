package com.vcolofati.zapzap.ui.home.fragments.conversation

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
import com.vcolofati.zapzap.databinding.ConversationFragmentBinding
import com.vcolofati.zapzap.ui.home.fragments.contact.adapter.ContactsAdapter
import com.vcolofati.zapzap.ui.home.fragments.conversation.adapter.ConversationAdapter
import com.vcolofati.zapzap.utils.RecyclerItemClickListener
import com.vcolofati.zapzap.utils.startChatActivity

class ConversationFragment : Fragment() {

    private val viewModel: ConversationViewModel by viewModels()
    private var _binding: ConversationFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.conversation_fragment, container, false)
        val view = binding.root
        // Adapter
        val adapter = ConversationAdapter(Glide.with(requireActivity()))
        // Recycler View
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.conversationRecycler.layoutManager = layoutManager
        binding.conversationRecycler.setHasFixedSize(true)
        binding.conversationRecycler.adapter = adapter
        adapter.bindList(emptyList())
        setObservers(adapter)
        binding.conversationRecycler.addOnItemTouchListener(object : RecyclerItemClickListener(activity,
            binding.conversationRecycler,
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

    private fun setObservers(adapter: ConversationAdapter) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}