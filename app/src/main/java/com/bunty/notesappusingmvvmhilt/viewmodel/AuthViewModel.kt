package com.bunty.notesappusingmvvmhilt.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunty.notesappusingmvvmhilt.helper.Helper
import com.bunty.notesappusingmvvmhilt.models.UserRequest
import com.bunty.notesappusingmvvmhilt.models.UserResponse
import com.bunty.notesappusingmvvmhilt.repository.UserRepository
import com.bunty.notesappusingmvvmhilt.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun signUpUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun validateUser(
        user: String, email: String, password: String, isLogin: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(user)) || TextUtils.isEmpty(email) || TextUtils.isEmpty(
                password
            )
        ) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Helper.isValidEmail(email)) {
            result = Pair(false, "Email is invalid")
        } else if (!TextUtils.isEmpty(password) && password.length <= 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}