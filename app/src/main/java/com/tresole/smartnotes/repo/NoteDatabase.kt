package com.tresole.smartnotes.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase :RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
    companion object {

    private lateinit var INSTANCE: NoteDatabase
     fun getDatabase(context: Context): NoteDatabase {
        synchronized(NoteDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NoteDatabase::class.java,
                    "notes").build()
            }
        }
        return INSTANCE
    }
}}