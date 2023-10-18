package com.dzakyadlh.storytell.ui.detail

import androidx.lifecycle.ViewModel
import com.dzakyadlh.storytell.data.repository.StoryRepository

class DetailViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getDetailStory(id: String) = storyRepository.getDetailStory(id)

}