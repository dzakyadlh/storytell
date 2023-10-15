package com.dzakyadlh.storytell.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyadlh.storytell.data.repository.UserRepository
import com.dzakyadlh.storytell.data.response.RegisterResponse
import kotlinx.coroutines.launch
import com.dzakyadlh.storytell.data.Result

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

//    fun register(name: String, email: String, password: String) = liveData {
//        try {
//            emit(repository.register(name, email, password))
//        } catch (e: Exception) {
//            emit(Result.Error("Network request failed: ${e.message}"))
//        }
//    }

//    fun register(name: String, email: String, password: String) {
//        viewModelScope.launch {
//            repository.register(name, email, password)
//        }
//    }

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()

    val registerResult: LiveData<Result<RegisterResponse>>
        get() = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _registerResult.value = Result.Loading
                val successResponse = repository.register(name, email, password)
                _registerResult.value = Result.Success(successResponse)
            } catch (e: Exception) {
                _registerResult.value = Result.Error("Network request failed: ${e.message}")
            }
        }
    }
}