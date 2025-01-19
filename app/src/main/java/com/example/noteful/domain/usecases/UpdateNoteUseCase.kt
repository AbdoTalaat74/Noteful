package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class UpdateNoteUseCase(
    private val repository: NotesRepository
) {
    suspend fun updateNote(note: Note) {
        repository.updateNote(note)
    }
}