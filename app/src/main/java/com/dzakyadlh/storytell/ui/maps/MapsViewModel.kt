package com.dzakyadlh.storytell.ui.maps

import androidx.lifecycle.ViewModel
import com.dzakyadlh.storytell.data.repository.StoryRepository

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStoryLocation() = repository.getLocationStory()
}