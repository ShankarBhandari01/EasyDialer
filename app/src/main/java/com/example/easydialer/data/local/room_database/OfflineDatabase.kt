package com.example.easydialer.data.local.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class OfflineDatabase : RoomDatabase() {
    abstract fun noteDoa(): NoteDao
}