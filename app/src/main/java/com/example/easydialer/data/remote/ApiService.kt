package com.example.easydialer.data.remote


import com.example.easydialer.data.ApiResponseData
import com.example.easydialer.data.Post
import com.example.easydialer.utils.Constants
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET(Constants.AGENT)
    suspend fun getAgents(): Response<ApiResponseData>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}