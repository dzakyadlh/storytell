package com.dzakyadlh.storytell.ui.signup

import androidx.lifecycle.ViewModel
import com.dzakyadlh.storytell.data.repository.UserRepository

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}