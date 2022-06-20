package com.vcolofati.zapzap.ui.home.chat

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.data.models.User
import com.vcolofati.zapzap.databinding.ActivityChatBinding
import com.vcolofati.zapzap.ui.configuration.CONTACT_KEY
import com.vcolofati.zapzap.ui.configuration.REQUEST_GALLERY
import com.vcolofati.zapzap.ui.configuration.REQUEST_IMAGE_CAPTURE
import com.vcolofati.zapzap.ui.home.chat.adapter.MessagesAdapter
import com.vcolofati.zapzap.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChatBinding
    private val viewmodel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.content.ui = this
        binding.content.viewmodel = viewmodel

        // Pegar intent
        val bundle = intent.extras
        val user: User = bundle?.getSerializable(CONTACT_KEY) as User

        //Inicializar toolbar
        val toolbar = binding.chatToolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // inicializar Toolbar com dados do contato
        binding.userNameToolbar.text = user.name
        val uri = Uri.parse(user.imageUrl)
        val glideInstance = Glide.with(this)
        if (user.imageUrl != null) {
            glideInstance
                .load(uri)
                .into(binding.circleImageToolbar)
        } else {
            binding.circleImageToolbar.setImageResource(R.drawable.default_image)
        }
        viewmodel.bindContact(user)

        // configuração adapter
        val adapter = this.viewmodel.createAdapter(glideInstance)

        //configuração layoutmanager
        val layputManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        binding.content.recyclerMessages.layoutManager = layputManager
        binding.content.recyclerMessages.setHasFixedSize(true)
        binding.content.recyclerMessages.adapter = adapter

        setObservers(adapter)
    }

    override fun onStart() {
        super.onStart()
        this.viewmodel.getMessages()
    }

    override fun onStop() {
        this.viewmodel.detachMessageListener()
        super.onStop()
    }

    private fun setObservers(adapter: MessagesAdapter) {
        this.viewmodel.messagesList.observe(this) { list ->
            adapter.bindList(list)
        }
    }

    fun dispatchGalleryIntent(view: View) {
        getResult.launch("image/*")
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
                val image = getCapturedImage(it)
                viewmodel.save(image)
        }

    private fun getCapturedImage(selectedPhotoUri: Uri): Bitmap {
        return when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }
}