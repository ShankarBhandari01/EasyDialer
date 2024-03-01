package com.example.easydialer.data.remote


import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.DataModel
import com.example.easydialer.utils.Constants.Companion.API_AGENT
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(API_AGENT)
    suspend fun getAgents(): Response<List<DataModel>>

    @GET(API_CAMPAIGN)
    suspend fun getCAMPAIGN(): Response<CampaignResponse>

}