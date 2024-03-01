package com.example.easydialer.repository

import com.example.easydialer.data.local.room_database.NoteEntity
import kotlinx.coroutines.flow.Flow

interface OfflineStoreRepository {
    fun getFirstLaunch(): Flow<Boolean>
    suspend fun saveFirstLaunch(isFirstLaunch: Boolean)
    suspend fun insertNote(noteEntity: NoteEntity)
    fun getNote(): NoteEntity
}