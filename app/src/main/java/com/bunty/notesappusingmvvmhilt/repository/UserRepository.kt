package com.bunty.notesappusingmvvmhilt.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bunty.notesappusingmvvmhilt.api.ApiInterface
import com.bunty.notesappusingmvvmhilt.models.UserRequest
import com.bunty.notesappusingmvvmhilt.models.UserResponse
import com.bunty.notesappusingmvvmhilt.utils.Constants.TAG
import com.bunty.notesappusingmvvmhilt.utils.NetworkResult
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiInterface: ApiInterface) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = apiInterface.userSignUp(userRequest)
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = apiInterface.userSignIn(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            Log.d(TAG, "responseRegister success:- ${response.body().toString()}")
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(message = errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error(message = "Something went wrong"))
            Log.d(TAG, "responseRegister failure:- ${response.body().toString()}")
        }
    }

}