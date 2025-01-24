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
    suspend fun getNoteById(id: Int): Note?
    suspend fun searchNotes(query: String): List<Note>
    suspend fun deleteNote(note: Note)
    suspend fun updateCategory(oldName: String, newName: String)
    suspend fun deleteCategory(category: Category)
    suspend fun deleteCategoryWithNotes(category: Category)
}