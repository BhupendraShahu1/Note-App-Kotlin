package com.example.notes.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesData")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val tittle: String,
    val content: String,
    val time: String
) {
constructor(  tittle: String,
              content: String,
              time: String):this(null,tittle,content,time)
}
