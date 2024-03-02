package com.example.easydialer.repository

import android.content.Context
import com.example.easydialer.data.remote.RemoteDataSource
import com.example.easydialer.models.CampaignResponse
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
}