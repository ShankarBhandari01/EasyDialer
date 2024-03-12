package com.example.easydialer.data.remote

import com.example.easydialer.models.Login
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAgents() = apiService.getAgents()
    suspend fun getCampaign() = apiService.getCAMPAIGN()
    suspend fun getCampaignAgent(id: Int) = apiService.getCampaignAgent(id)

    suspend fun getCampaignDisposition(id: Int) = apiService.getCampaignDisposition(id)
    suspend fun getCampaignMobile(id: Int) = apiService.getCampaignMobile(id)
    suspend fun login(login: Login) = apiService.login(login)
}