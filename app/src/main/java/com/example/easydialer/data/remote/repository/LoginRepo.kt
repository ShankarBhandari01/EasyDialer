package com.example.easydialer.data.remote.repository

import android.content.Context
import com.example.easydialer.data.remote.DataModel
import com.example.easydialer.data.remote.Post
import com.example.easydialer.data.remote.RemoteDataSource
import com.example.easydialer.utils.toResultFlow
import com.example.easydialer.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepo @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getAgents(context: Context): Flow<NetWorkResult<List<DataModel>>> {
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