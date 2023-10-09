package com.dzakyadlh.storytell.di

import android.content.Context
import com.dzakyadlh.storytell.data.UserRepository
import com.dzakyadlh.storytell.data.pref.UserPreference
import com.dzakyadlh.storytell.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}