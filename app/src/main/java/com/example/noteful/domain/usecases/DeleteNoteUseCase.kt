package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Note
import com.example.noteful.domain.repository.NotesRepository

class DeleteNoteUseCase(
    private val repository: NotesRepository
){
    suspend fun deleteNote(note: Note){
        repository.deleteNote(note)

    }
}