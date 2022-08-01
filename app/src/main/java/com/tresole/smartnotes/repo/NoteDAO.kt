package com.tresole.smartnotes.repo

import androidx.room.*

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>
    @Insert
    fun insert(note: Note)
    @Delete
    fun delete(note: Note)
    @Update
    fun update(note: Note)
}