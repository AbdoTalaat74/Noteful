package com.example.noteful.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.noteful.data.local.relations.CategoryWithNotes
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(category: Category)


    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM Note")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM NOTE WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?


    @Query("SELECT * FROM Note WHERE text LIKE '%' || :query || '%' OR categoryName LIKE '%' || :query || '%'")
    suspend fun searchNotes(query: String): List<Note>


    @Query("SELECT * FROM Category WHERE categoryName = :categoryName")
    suspend fun getCategoryByName(categoryName: String): Category?

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Transaction
    @Query("SELECT * FROM Category WHERE categoryName = :categoryName")
    suspend fun getCategoryWithNotes(categoryName: String):List<CategoryWithNotes>

    @Query("UPDATE Category SET categoryName = :newName WHERE categoryName = :oldName")
    suspend fun updateCategoryName(oldName: String, newName: String)

    @Query("UPDATE Note SET categoryName = :newName WHERE categoryName = :oldName")
    suspend fun updateNotesCategoryName(oldName: String, newName: String)

    @Transaction
    suspend fun updateCategoryAndNotes(oldName: String, newName: String) {
        updateCategoryName(oldName, newName)
        updateNotesCategoryName(oldName, newName)
    }

    @Query("DELETE FROM Note WHERE categoryName = :categoryName")
    suspend fun deleteNotesByCategory(categoryName: String)

    @Transaction
    suspend fun deleteCategoryWithNotes(category: Category) {
        deleteNotesByCategory(category.categoryName)
        deleteCategory(category)
    }
}