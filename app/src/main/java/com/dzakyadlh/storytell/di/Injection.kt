package com.dzakyadlh.storytell.di

import android.content.Context
import com.dzakyadlh.storytell.data.pref.UserPreference
import com.dzakyadlh.storytell.data.pref.dataStore
import com.dzakyadlh.storytell.data.repository.StoryRepository
import com.dzakyadlh.storytell.data.repository.UserRepository
import com.dzakyadlh.storytell.data.retrofit.APIConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = APIConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(): StoryRepository {
        val apiService = APIConfig.getApiService()
        return StoryRepository.getInstance(apiService)
    }
}