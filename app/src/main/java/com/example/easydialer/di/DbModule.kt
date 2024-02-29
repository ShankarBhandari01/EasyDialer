package com.example.easydialer.di

import android.content.Context
import androidx.room.Room
import com.example.easydialer.data.local.room_database.NoteEntity
import com.example.easydialer.data.local.room_database.OfflineDatabase
import com.example.easydialer.utils.Constants.Companion.OFFLINE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, OfflineDatabase::class.java, OFFLINE_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: OfflineDatabase) = db.noteDoa()

    @Provides
    fun provideEntity() = NoteEntity()
}