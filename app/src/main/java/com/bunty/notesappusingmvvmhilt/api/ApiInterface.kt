package com.bunty.notesappusingmvvmhilt.api

import com.bunty.notesappusingmvvmhilt.models.UserRequest
import com.bunty.notesappusingmvvmhilt.models.UserResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("users/signup")
    suspend fun userSignUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("users/signin")
    suspend fun userSignIn(@Body userRequest: UserRequest): Response<UserResponse>

}