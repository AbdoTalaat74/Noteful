package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class AddNoteUseCase(
    private val newsRepository: NotesRepository
) {
    suspend fun addNote(note: Note){
        newsRepository.upsertNote(note)
    }
}