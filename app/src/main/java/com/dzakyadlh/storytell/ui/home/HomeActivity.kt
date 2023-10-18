package com.dzakyadlh.storytell.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyadlh.storytell.R
import com.dzakyadlh.storytell.data.Result
import com.dzakyadlh.storytell.data.response.ListStoryItem
import com.dzakyadlh.storytell.databinding.ActivityHomeBinding
import com.dzakyadlh.storytell.ui.StoryViewModelFactory
import com.dzakyadlh.storytell.ui.UserViewModelFactory
import com.dzakyadlh.storytell.ui.landing.LandingActivity
import com.dzakyadlh.storytell.ui.main.MainViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel by viewModels<HomeViewModel> {
        StoryViewModelFactory.getInstance(this)
    }

    private val userViewModel by viewModels<MainViewModel> {
        UserViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.storyList.layoutManager = layoutManager

        setupView()
        setupAction()
        playAnimation()

        binding.appBarLayout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    userViewModel.logout()
                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
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

    private fun setupAction() {
        viewModel.getAllStory().observe(this@HomeActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        setListStory(result.data)
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

    private fun setListStory(storyResponse: List<ListStoryItem>) {
        val adapter = HomeAdapter()
        adapter.submitList(storyResponse)
        binding.storyList.adapter = adapter
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.storyList, View.ALPHA, 1f).setDuration(300).start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}