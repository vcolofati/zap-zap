package com.vcolofati.zapzap.ui.home.fragments.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vcolofati.zapzap.R

class ConversationFragment : Fragment() {

    companion object {
        fun newInstance() = ConversationFragment()
    }

    private val viewModel: ConversationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.conversation_fragment, container, false)
    }
}