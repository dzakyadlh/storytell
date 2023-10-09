package com.dzakyadlh.storytell.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyadlh.storytell.data.UserRepository
import com.dzakyadlh.storytell.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository):ViewModel() {
    fun saveSession(user:UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}