package com.tresole.smartnotes.helpers

import com.tresole.smartnotes.repo.Note

class CurrentNote {
    companion object{
     var note : Note =Note("","",false,false)
    fun setCurrent(note: Note)
    {
        this.note=note
    }
    fun getCurrent(): Note {
        return note
    }
}}