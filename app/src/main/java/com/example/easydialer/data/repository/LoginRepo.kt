package com.example.easydialer.data.repository

import android.content.Context
import com.example.easydialer.data.ApiResponseData
import com.example.easydialer.data.Post
import com.example.easydialer.data.remote.RemoteDataSource
import com.example.easydialer.data.remote.toResultFlow
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepo @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getAgents(context: Context): Flow<NetWorkResult<ApiResponseData>> {
        return toResultFlow(context) {
            remoteDataSource.getAgents()
        }
    }

    suspend fun getPostList(context: Context): Flow<NetWorkResult<List<Post>>> {
        return toResultFlow(context) {
            remoteDataSource.getPosts()
        }
    }
}