package com.example.noteful.data.repository

import com.example.noteful.data.local.NotesDao
import com.example.noteful.data.local.relations.CategoryWithNotes
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class RepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {
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

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun searchNotes(query: String): List<Note> {
        return dao.searchNotes(query)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun updateCategory(oldName: String, newName: String) {
        dao.updateCategoryName(oldName,newName)
        dao.updateNotesCategoryName(oldName,newName)
        dao.updateNotesCategoryName(oldName, newName)
    }

    override suspend fun deleteCategory(category: Category) {
        dao.deleteCategory(category)
    }

    override suspend fun deleteCategoryWithNotes(category: Category) {
        dao.deleteCategoryWithNotes(category)
    }

    override suspend fun getFavoriteNotes(): List<Note> {
        return dao.getFavoriteNotes()
    }

}