package com.example.easydialer.di

import com.example.easydialer.data.local.OfflineStoreRepository
import com.example.easydialer.data.local.OfflineStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindDataStore(dataStoreImpl: OfflineStoreImpl): OfflineStoreRepository
}