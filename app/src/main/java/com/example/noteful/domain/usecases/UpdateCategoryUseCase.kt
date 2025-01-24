package com.example.noteful.domain.usecases

import com.example.noteful.domain.repository.NotesRepository

class UpdateCategoryUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(oldName: String, newName: String) =
        repository.updateCategory(oldName, newName)
}