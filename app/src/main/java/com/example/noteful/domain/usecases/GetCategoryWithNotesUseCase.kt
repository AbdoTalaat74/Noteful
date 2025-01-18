package com.example.noteful.domain.usecases

import com.example.noteful.domain.repository.NotesRepository

class GetCategoryWithNotesUseCase(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(categoryName: String) = notesRepository.getCategoryWithNotes(categoryName)
}