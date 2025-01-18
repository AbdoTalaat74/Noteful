package com.example.noteful.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note

@Database(
    entities = [Note::class, Category::class],
    version = 2
)
abstract class NotesDatabase:RoomDatabase() {
    abstract val notesDao:NotesDao
}