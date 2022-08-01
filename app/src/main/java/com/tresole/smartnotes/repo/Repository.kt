package com.tresole.smartnotes.repo

import android.content.Context

class Repository(context: Context) {
   val database=NoteDatabase.getDatabase(context)
    fun insert(note: Note){
        database.noteDAO().insert(note)
    }
    fun delete(note: Note){
       database.noteDAO().delete(note)
    }
    fun update(note: Note){
     database.noteDAO().update(note)
    }
    fun load(): List<Note> {
        return database.noteDAO().getAll()
    }
}