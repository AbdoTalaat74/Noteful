package com.example.noteful.data.repository

import com.example.noteful.data.local.NotesDao
import com.example.noteful.data.local.relations.CategoryWithNotes
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class RepositoryImpl(
    private val dao: NotesDao
):NotesRepository {
    override suspend fun getNotes(): List<Note> {
       return dao.getNotes()
    }

    override suspend fun upsertNote(note: Note) {
        dao.upsertNote(note)
    }

    override suspend fun upsertCategory(category: Category) {
        dao.upsertCategory(category)
    }

    override suspend fun getCategoryWithNotes(categoryName: String): List<CategoryWithNotes> {
        return dao.getCategoryWithNotes(categoryName)
    }

    override suspend fun getCategories(): List<Category> {
        return dao.getCategories()
    }
}