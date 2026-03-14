package com.example.backend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/")
    fun checkApi(): Call<ApiResponse>

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<ApiResponse>

    @POST("login")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<ApiResponse>

}