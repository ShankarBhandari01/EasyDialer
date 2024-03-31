package com.example.easydialer.data.remote


import com.example.easydialer.models.AgentList
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.Agent
import com.example.easydialer.models.CampaignSummary
import com.example.easydialer.models.DispositionList
import com.example.easydialer.models.Login
import com.example.easydialer.models.MobileList
import com.example.easydialer.models.LoginResponse
import com.example.easydialer.models.MobileListItem
import com.example.easydialer.utils.Constants.Companion.API_AGENT
import com.example.easydialer.utils.Constants.Companion.API_AGENT_PROFILE
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_AGENT
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_DISPOSITION
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_INFO_BY_ID
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_MOBILE
import com.example.easydialer.utils.Constants.Companion.API_CAMPAIGN_UPDATE_MOBILE
import com.example.easydialer.utils.Constants.Companion.API_LOGON
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET(API_AGENT)
    suspend fun getAgents(): Response<List<Agent>>

    @GET(API_CAMPAIGN)
    suspend fun getCAMPAIGN(): Response<CampaignResponse>

    @GET(API_CAMPAIGN_AGENT)
    suspend fun getCampaignAgent(@Path("id") id: Int): Response<AgentList>

    @GET(API_CAMPAIGN_DISPOSITION)
    suspend fun getCampaignDisposition(@Path("id") id: Int): Response<DispositionList>

    @GET(API_CAMPAIGN_MOBILE)
    suspend fun getCampaignMobile(@Path("id") id: Int): Response<MobileList>

    @POST(API_LOGON)
    suspend fun login(@Body login: Login): Response<LoginResponse>

    @GET(API_AGENT_PROFILE)
    suspend fun getAgentProfile(@Path("id") id: Int): Response<Agent>

    @GET(API_CAMPAIGN_INFO_BY_ID)
    suspend fun getCampaignInfoById(@Path("id") id: Int): Response<CampaignSummary>
    /*
        @POST(API_CAMPAIGN_UPDATE_MOBILE)
        suspend fun updateCampaignMobile(@Body mobile: MobileListItem): Response<>*/


}