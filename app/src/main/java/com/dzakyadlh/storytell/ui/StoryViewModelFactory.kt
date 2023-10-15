package com.dzakyadlh.storytell.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyadlh.storytell.data.repository.StoryRepository
import com.dzakyadlh.storytell.di.Injection
import com.dzakyadlh.storytell.ui.newstory.NewStoryViewModel

class StoryViewModelFactory(private val repository: StoryRepository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewStoryViewModel::class.java)->{
                NewStoryViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(Injection.provideStoryRepository())
            }.also { instance = it }
    }
}