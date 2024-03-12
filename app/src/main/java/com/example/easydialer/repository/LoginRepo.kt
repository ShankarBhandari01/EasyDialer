package com.example.easydialer.repository

import android.content.Context
import com.example.easydialer.models.DataModel
import com.example.easydialer.data.remote.RemoteDataSource
import com.example.easydialer.models.Login
import com.example.easydialer.models.LoginResponse
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

    suspend fun login(context:Context,login: Login):Flow<NetWorkResult<LoginResponse>> {
        return toResultFlow(context){
            remoteDataSource.login(login = login)
        }
    }

}