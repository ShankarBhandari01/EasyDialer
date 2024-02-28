package com.example.easydialer.di

import com.example.easydialer.data.local.DataStoreRepository
import com.example.easydialer.data.local.DataStoreImpl
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
    abstract fun bindDataStore(dataStoreImpl: DataStoreImpl): DataStoreRepository
}