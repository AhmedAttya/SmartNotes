package com.tresole.smartnotes.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="notebody") val notebody:String,
    @ColumnInfo(name="favourite") var favourite :Boolean,
    @ColumnInfo(name="trash") var trash :Boolean,
    @PrimaryKey(autoGenerate = true) val uid: Int=0
)
