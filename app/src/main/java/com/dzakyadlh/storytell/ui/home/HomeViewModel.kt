package com.dzakyadlh.storytell.ui.home

import androidx.lifecycle.ViewModel
import com.dzakyadlh.storytell.data.repository.StoryRepository

class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStory() = storyRepository.getAllStory()

}