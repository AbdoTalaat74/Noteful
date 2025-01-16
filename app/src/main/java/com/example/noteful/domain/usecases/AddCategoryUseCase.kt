package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Category
import com.example.noteful.domain.repository.NotesRepository

class AddCategoryUseCase(
    private val notesRepository: NotesRepository
) {
    suspend fun addCategory(category: Category){
        notesRepository.upsertCategory(category)

    }
}