package com.example.easydialer.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAgents() = apiService.getAgents()
    suspend fun getCampaign() = apiService.getCAMPAIGN()
}