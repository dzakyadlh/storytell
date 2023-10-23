package com.dzakyadlh.storytell.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzakyadlh.storytell.R
import com.dzakyadlh.storytell.databinding.ActivityHomeBinding
import com.dzakyadlh.storytell.ui.StoryViewModelFactory
import com.dzakyadlh.storytell.ui.UserViewModelFactory
import com.dzakyadlh.storytell.ui.landing.LandingActivity
import com.dzakyadlh.storytell.ui.main.MainViewModel
import com.dzakyadlh.storytell.ui.maps.MapsActivity
import com.dzakyadlh.storytell.ui.newstory.NewStoryActivity

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
        setListStory()
        playAnimation()

        binding.appBarLayout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.maps -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }

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

        binding.addStoryButton.setOnClickListener {
            startActivity(Intent(this, NewStoryActivity::class.java))
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

    private fun setListStory() {
        val adapter = HomeAdapter()
        binding.storyList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getAllStory.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.storyList, View.ALPHA, 1f).setDuration(300).start()
    }

}