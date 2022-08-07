package com.tresole.smartnotes.note

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tresole.smartnotes.helpers.CurrentNote
import com.tresole.smartnotes.repo.Note
import com.tresole.smartnotes.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

class NoteViewModel(val repo: Repository): ViewModel() {
      val note=CurrentNote.getCurrent()
    var result =MutableLiveData<Boolean>()

    fun update(){
        GlobalScope.launch {
            Log.e("ggez","note update")
            repo.update(note)
        }
    }
    fun save(note: Note) {
        GlobalScope.launch {
            Log.e("ggez","note save")
         repo.insert(note)
        }
    }

    fun removefavourite() {
        GlobalScope.launch {
            note.favourite=false
            repo.update(note)
        }
    }

    fun addfavourite() {
        GlobalScope.launch {
            note.favourite=true
            repo.update(note)
        }
    }

    fun delete() {
        GlobalScope.launch {
            repo.delete(note)
        }
    }

    fun movetotrash() {
        GlobalScope.launch {
            note.trash=true
            repo.update(note)
        }
    }

    fun restore() {
        GlobalScope.launch {
            note.trash=false
            repo.update(note)
        }
    }

    fun checkifnew() {

        GlobalScope.launch {
         Log.e("ggez","note save checking")
          if (repo.exist(note.uid)){
             repo.update(note)
              Log.e("ggez","note updated")
          }
          else{
              repo.insert(note)
              Log.e("ggez","note saved")
          }
    }
    }
}