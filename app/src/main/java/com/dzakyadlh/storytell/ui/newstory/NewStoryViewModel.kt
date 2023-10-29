package com.dzakyadlh.storytell.ui.newstory

import androidx.lifecycle.ViewModel
import com.dzakyadlh.storytell.data.repository.StoryRepository
import java.io.File

class NewStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun newStory(file: File, description: String, lat: Double? = null, lon: Double? = null) =
        repository.newStory(file, description, lat, lon)
}