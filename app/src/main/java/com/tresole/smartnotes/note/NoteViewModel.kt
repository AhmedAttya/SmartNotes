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



}