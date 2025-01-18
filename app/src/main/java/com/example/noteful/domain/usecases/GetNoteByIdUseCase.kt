package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class GetNoteByIdUseCase(
    private val repository: NotesRepository

) {
    suspend fun getNoteById(id: Int): Note? {
        return repository.getNoteById(id)
    }

}