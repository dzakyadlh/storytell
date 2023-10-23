package com.dzakyadlh.storytell.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dzakyadlh.storytell.data.repository.StoryRepository
import com.dzakyadlh.storytell.di.Injection
import com.dzakyadlh.storytell.ui.detail.DetailViewModel
import com.dzakyadlh.storytell.ui.home.HomeViewModel
import com.dzakyadlh.storytell.ui.maps.MapsViewModel
import com.dzakyadlh.storytell.ui.newstory.NewStoryViewModel

class StoryViewModelFactory(private val repository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewStoryViewModel::class.java) -> {
                NewStoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(Injection.provideStoryRepository(context))
            }.also { instance = it }
    }
}