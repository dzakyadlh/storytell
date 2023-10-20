package com.dzakyadlh.storytell.ui.newstory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dzakyadlh.storytell.R
import com.dzakyadlh.storytell.data.Result
import com.dzakyadlh.storytell.databinding.ActivityNewStoryBinding
import com.dzakyadlh.storytell.ui.StoryViewModelFactory
import com.dzakyadlh.storytell.ui.home.HomeActivity
import com.dzakyadlh.storytell.utils.getImageUri
import com.dzakyadlh.storytell.utils.reduceFileImage
import com.dzakyadlh.storytell.utils.uriToFile

class NewStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStoryBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<NewStoryViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        playAnimation()

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Gallery picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.descEditText.text.toString()
            viewModel.newStory(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            result.data.message?.let { showToast(it) }
                            showLoading(false)
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun playAnimation() {
        val previewImg =
            ObjectAnimator.ofFloat(binding.previewImageView, View.ALPHA, 1f).setDuration(300)
        val descEditText =
            ObjectAnimator.ofFloat(binding.descEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val galleryButton =
            ObjectAnimator.ofFloat(binding.galleryButton, View.ALPHA, 1f).setDuration(300)
        val cameraButton =
            ObjectAnimator.ofFloat(binding.cameraButton, View.ALPHA, 1f).setDuration(300)
        val uploadButton =
            ObjectAnimator.ofFloat(binding.uploadButton, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                previewImg, descEditText, galleryButton, cameraButton, uploadButton
            )
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }
}