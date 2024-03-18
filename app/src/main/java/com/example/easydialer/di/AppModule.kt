package com.example.easydialer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.easydialer.service.EasyDialerService
import com.example.easydialer.utils.Constants.Companion.USER_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.userPreference by preferencesDataStore(name = USER_PREFERENCES)

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userPreference


    @Provides
    fun provideMyForegroundService(): EasyDialerService {
        return EasyDialerService()
    }

}