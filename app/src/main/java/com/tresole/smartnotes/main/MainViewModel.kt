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
    val list=MutableLiveData<MutableList<Note>>()
    fun loadall() {
        viewModelScope.launch {
         list.value= repo.load().toMutableList()
            list.value?.sortByDescending { it.uid }
        }
    }

    fun setcurrent(note: Note) {
        CurrentNote.setCurrent(note)
    }

    fun loadfavourite() {
       viewModelScope.launch {
           list.value=repo.loadfavourite()?.toMutableList()
           list.value?.sortByDescending { it.uid }
       }
    }

    fun loadtrash() {
        viewModelScope.launch {
            list.value=repo.loadtrash()?.toMutableList()
            list.value?.sortByDescending { it.uid }
        }
    }


}