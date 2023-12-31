package com.dzakyadlh.storytell.ui.detail

import android.content.Intent.EXTRA_TEXT
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dzakyadlh.storytell.data.Result
import com.dzakyadlh.storytell.databinding.ActivityDetailBinding
import com.dzakyadlh.storytell.ui.StoryViewModelFactory
import com.dzakyadlh.storytell.utils.DateFormatter

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_TEXT).toString()

        setupView()
        setupAction(storyId)

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(id: String) {
        with(binding) {
            viewModel.getDetailStory(id).observe(this@DetailActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            Glide.with(this@DetailActivity).load(result.data.photoUrl)
                                .into(storyImg)
                            storyName.text = result.data.name
                            storyDesc.text = result.data.description
                            storyDate.text =
                                DateFormatter.formatDate(result.data.createdAt.toString())
                            showLoading(false)
                        }

                        is Result.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}