package com.example.easydialer.repository

import android.content.Context
import com.example.easydialer.data.remote.RemoteDataSource
import com.example.easydialer.models.AgentList
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.CampaignSummary
import com.example.easydialer.models.CampaignUpdateResponse
import com.example.easydialer.models.DispositionList
import com.example.easydialer.models.DispositionUpdate
import com.example.easydialer.models.MobileList
import com.example.easydialer.utils.NetWorkResult
import com.example.easydialer.utils.toResultFlow
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class CampaignRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getCampaign(context: Context): Flow<NetWorkResult<CampaignResponse>> {
        return toResultFlow(context) {
            remoteDataSource.getCampaign()
        }
    }

    suspend fun getCampaignAgent(context: Context, id: Int): Flow<NetWorkResult<AgentList>> {
        return toResultFlow(context) {
            remoteDataSource.getCampaignAgent(id)
        }
    }

    suspend fun getCampaignDisposition(
        context: Context,
        id: Int
    ): Flow<NetWorkResult<DispositionList>> {
        return toResultFlow(context) {
            remoteDataSource.getCampaignDisposition(id)
        }
    }

    suspend fun getCampaignMobile(context: Context, id: Int): Flow<NetWorkResult<MobileList>> {
        return toResultFlow(context) {
            remoteDataSource.getCampaignMobile(id)
        }
    }

    suspend fun getCampaignSummary(
        context: Context,
        id: Int
    ): Flow<NetWorkResult<CampaignSummary>> {
        return toResultFlow(context) {
            remoteDataSource.getCampaignSummary(id)
        }
    }

    suspend fun updateCampaignMobile(
        context: Context,
        dispositionUpdate: DispositionUpdate
    ): Flow<NetWorkResult<CampaignUpdateResponse>> {
        return toResultFlow(context) {
            remoteDataSource.updateCampaignMobile(dispositionUpdate)
        }
    }

}