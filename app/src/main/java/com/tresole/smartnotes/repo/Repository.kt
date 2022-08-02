package com.tresole.smartnotes.repo

import android.content.Context
import android.util.Log

class Repository(context: Context) {
   val database=NoteDatabase.getDatabase(context)
    suspend fun insert(note: Note){
        database.noteDAO().insert(note)
    }
    suspend fun delete(note: Note){
       database.noteDAO().delete(note)
    }
    suspend fun update(note: Note){
     database.noteDAO().update(note)
    }
    suspend fun load(): List<Note> {
        return database.noteDAO().getAll()
    }

}