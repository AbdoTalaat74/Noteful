package com.example.noteful.domain.repository

import com.example.noteful.data.local.relations.CategoryWithNotes
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note

interface NotesRepository {
    suspend fun getNotes(): List<Note>

    suspend fun upsertNote(note: Note)

    suspend fun upsertCategory(category: Category)
    suspend fun getCategoryWithNotes(categoryName: String): List<CategoryWithNotes>
    suspend fun getCategories(): List<Category>

}