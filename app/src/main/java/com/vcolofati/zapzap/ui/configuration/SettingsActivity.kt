package com.vcolofati.zapzap.ui.configuration

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.vcolofati.zapzap.R
import com.vcolofati.zapzap.databinding.ActivitySettingsBinding
import com.vcolofati.zapzap.utils.Status

import com.vcolofati.zapzap.utils.toast

private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_GALLERY = 2

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val viewModel: SettingsViewModel by viewModels()
    private var actualUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.ui = this
        binding.viewmodel = this.viewModel
        // Configure Toolbar
        val toolbar = binding.toolbar as Toolbar
        toolbar.title = "Configurações"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.setObservers()
        this.viewModel.fetchUri()
    }

    private fun setObservers() {
        this.viewModel.getUri().observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> binding.progressLoadImage.visibility = View.VISIBLE
                Status.SUCESS -> {
                    loadProfileImage(resource.data)
                    actualUri = resource.data
                    binding.progressLoadImage.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressLoadImage.visibility = View.GONE
                }
            }
        }
    }

    private fun loadProfileImage(uri: Uri?) {
        if (uri != null) {
            Glide.with(this)
                .load(uri)
                .thumbnail(Glide.with(this)
                    .load(actualUri)
                    .fitCenter()
                )
                .fitCenter()
                .into(binding.profileImage)
        } else {
            binding.profileImage.setImageResource(R.drawable.default_image)
        }
    }

    fun dispatchTakePictureIntent(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            toast("Não foi possível abrir a camera")
        }
    }

    fun dispatchGalleryIntent(view: View) {
        val takePictureIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_GALLERY)
        } else {
            toast("Não foi possível abrir a galeria")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val takenImage = data?.extras?.get("data") as Bitmap
                    viewModel.save(takenImage)
                }
                REQUEST_GALLERY -> {
                    val imagePath = data?.data
                    val image = MediaStore.Images.Media.getBitmap(this.contentResolver, imagePath)
                    viewModel.save(image)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}