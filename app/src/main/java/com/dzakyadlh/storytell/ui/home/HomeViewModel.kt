package com.dzakyadlh.storytell.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dzakyadlh.storytell.data.repository.StoryRepository
import com.dzakyadlh.storytell.data.response.ListStoryItem

class HomeViewModel(private val storyRepository: StoryRepository) : ViewModel() {
//    fun getAllStory() = storyRepository.getAllStory()

    val getAllStory: LiveData<PagingData<ListStoryItem>> = storyRepository.getAllStory()
        .cachedIn(viewModelScope)
}