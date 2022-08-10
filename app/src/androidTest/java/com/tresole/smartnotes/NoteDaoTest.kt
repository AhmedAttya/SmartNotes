package com.tresole.smartnotes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tresole.smartnotes.repo.Note
import com.tresole.smartnotes.repo.NoteDAO
import com.tresole.smartnotes.repo.NoteDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var NoteDao: NoteDAO
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java).build()
        NoteDao = db.noteDAO()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert_and_exist_and_update_and_delete_test() {
        runBlocking {
            var Note = Note("test", "test", false, false)
            NoteDao.insert(Note)
            assert(NoteDao.getAll().isNotEmpty())
            Note = NoteDao.getAll().first()
            Note.title = "new test"
            NoteDao.update(Note)
            assertEquals(NoteDao.getAll().first(), Note)
            NoteDao.delete(Note)
            assert(NoteDao.getAll().isEmpty())
        }
    }

}



