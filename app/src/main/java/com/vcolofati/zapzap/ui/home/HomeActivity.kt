package com.vcolofati.zapzap.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.databinding.ActivityHomeBinding
import com.vcolofati.zapzap.ui.home.fragments.contact.ContactsFragment
import com.vcolofati.zapzap.ui.home.fragments.conversation.ConversationFragment
import com.vcolofati.zapzap.utils.startLoginActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewmodel = viewModel
        val toolbar = binding.toolbar
        toolbar.title = "ZapZap"
        setSupportActionBar(toolbar)

        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("Conversas", ConversationFragment::class.java)
                .add("Contatos", ContactsFragment::class.java)
                .create()
        )

        val viewpager = binding.viewpager
        viewpager.adapter = adapter
        binding.viewpagertab.setViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {}
            R.id.menu_configuration -> {}
            R.id.menu_exit -> {
                viewModel.logout()
                startLoginActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}