package com.dzakyadlh.storytell.di

import android.content.Context
import com.dzakyadlh.storytell.data.local.database.StoryDatabase
import com.dzakyadlh.storytell.data.pref.UserPreference
import com.dzakyadlh.storytell.data.pref.dataStore
import com.dzakyadlh.storytell.data.repository.StoryRepository
import com.dzakyadlh.storytell.data.repository.UserRepository
import com.dzakyadlh.storytell.data.retrofit.APIConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = APIConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val storyDatabase = StoryDatabase.getDatabase(context)
        val apiService = APIConfig.getApiService(user.token)
        return StoryRepository.getInstance(storyDatabase, apiService)
    }
}