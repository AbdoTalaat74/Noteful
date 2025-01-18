package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class SearchNoteUseCase(
    private val repository: NotesRepository
) {

    suspend fun searchNote(query: String): List<Note> {
        return repository.searchNotes(query)
    }


}