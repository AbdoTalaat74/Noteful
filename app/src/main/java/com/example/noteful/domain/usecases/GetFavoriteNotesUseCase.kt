package com.example.noteful.domain.usecases

import com.example.noteful.domain.repository.NotesRepository

class GetFavoriteNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke() = repository.getFavoriteNotes()


}