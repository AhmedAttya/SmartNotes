package com.tresole.smartnotes.note

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tresole.smartnotes.helpers.CurrentNote
import com.tresole.smartnotes.repo.Note
import com.tresole.smartnotes.repo.Repository
import kotlinx.coroutines.launch

class NoteViewModel(val repo: Repository): ViewModel() {
    val note=CurrentNote.getCurrent()
    fun save(note: Note) {
        viewModelScope.launch {
         repo.update(note)
        }
    }

    fun removefavourite() {

        viewModelScope.launch {
            note.favourite=false
            repo.update(note)
        }
    }

    fun addfavourite() {
        viewModelScope.launch {
            note.favourite=true
            repo.update(note)
        }
    }

    fun delete() {
        viewModelScope.launch {
            repo.delete(note)
        }
    }

    fun movetotrash() {
        viewModelScope.launch {
            note.trash=true
            repo.update(note)
        }
    }

    fun restore() {
        viewModelScope.launch {
            note.trash=false
            repo.update(note)
        }
    }


}