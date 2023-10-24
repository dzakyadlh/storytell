package com.dzakyadlh.storytell.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dzakyadlh.storytell.data.pref.UserModel
import com.dzakyadlh.storytell.data.repository.UserRepository

class LandingViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}