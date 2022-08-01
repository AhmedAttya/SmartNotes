package com.tresole.smartnotes.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
                @ColumnInfo(name="title") val title:String,
                @ColumnInfo(name="notebody") val notebody:String,
                @ColumnInfo(name="favourite") val favourite :Boolean,
                @ColumnInfo(name="trash")val trash :Boolean,
                @PrimaryKey(autoGenerate = false) val uid: Int?=null
)
