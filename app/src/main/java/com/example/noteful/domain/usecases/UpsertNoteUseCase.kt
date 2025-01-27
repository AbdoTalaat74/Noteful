package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class UpsertNoteUseCase(
    private val newsRepository: NotesRepository
) {
    suspend fun upsertNote(note: Note){
        newsRepository.upsertNote(note)
    }
}