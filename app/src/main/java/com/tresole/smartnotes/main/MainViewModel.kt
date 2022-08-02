package com.tresole.smartnotes.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tresole.smartnotes.helpers.CurrentNote
import com.tresole.smartnotes.repo.Note
import com.tresole.smartnotes.repo.Repository
import kotlinx.coroutines.launch

class MainViewModel(val repo: Repository): ViewModel() {
    val list=MutableLiveData<List<Note>>()
    fun load() {
        viewModelScope.launch {
         list.value= repo.load()
        }
    }

    fun setcurrent(note: Note) {
        CurrentNote.setCurrent(note)
    }


}