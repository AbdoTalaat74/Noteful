package com.example.noteful.domain.usecases

import com.example.noteful.domain.repository.NotesRepository

class GetAllNotesUseCase(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke() = notesRepository.getNotes()
}