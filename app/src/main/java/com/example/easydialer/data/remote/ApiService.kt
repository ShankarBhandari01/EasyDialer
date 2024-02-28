package com.example.easydialer.data.remote


import com.example.easydialer.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.AGENT)
    suspend fun getAgents(): Response<List<DataModel>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}