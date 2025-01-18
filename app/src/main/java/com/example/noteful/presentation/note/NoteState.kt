package com.example.noteful.presentation.note

import com.example.noteful.domain.model.Note

data class NoteState(
    val note: Note,
    val isLoading: Boolean,
    val error: String?

)
