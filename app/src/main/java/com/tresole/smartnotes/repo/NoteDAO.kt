package com.tresole.smartnotes.repo

import androidx.room.*

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes")
  suspend  fun getAll(): List<Note>
    @Insert
  suspend  fun insert(note: Note)
    @Delete
  suspend  fun delete(note: Note)
    @Update
   suspend fun update(note: Note)
    @Query("SELECT EXISTS(SELECT * FROM notes WHERE uid = :id)")
    suspend fun exist(id :Int) :Boolean
}