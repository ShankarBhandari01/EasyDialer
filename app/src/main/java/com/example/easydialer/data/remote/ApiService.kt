package com.example.easydialer.data.remote


import com.example.easydialer.models.AgentList
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.DataModel
import com.example.easydialer.models.DispositionList
import com.example.easydialer.models.MobileList
import com.example.easydialer.utils.Constants.Companion.API_AGENT
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_AGENT
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_DISPOSITION
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_MOBILE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(API_AGENT)
    suspend fun getAgents(): Response<List<DataModel>>

    @GET(API_CAMPAIGN)
    suspend fun getCAMPAIGN(): Response<CampaignResponse>

    @GET(API_CAMPAIGN_AGENT)
    suspend fun getCampaignAgent(@Path("id") id: Int): Response<AgentList>

    @GET(API_CAMPAIGN_DISPOSITION)
    suspend fun getCampaignDisposition(@Path("id") id: Int): Response<DispositionList>

    @GET(API_CAMPAIGN_MOBILE)
    suspend fun getCampaignMobile(@Path("id") id: Int): Response<MobileList>


}