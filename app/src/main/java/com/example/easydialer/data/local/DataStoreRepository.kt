package com.example.easydialer.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getFirstLaunch(): Flow<Boolean>

    suspend fun saveFirstLaunch(isFirstLaunch: Boolean)
}