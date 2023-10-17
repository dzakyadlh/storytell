package com.dzakyadlh.storytell.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyadlh.storytell.data.pref.UserModel
import com.dzakyadlh.storytell.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository):ViewModel() {
    fun saveSession(user:UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email:String, password:String) = repository.login(email, password)
}