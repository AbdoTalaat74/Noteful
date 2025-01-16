package com.example.noteful.presentation.main

import com.example.noteful.domain.model.Note

data class NoteState(
    val notes:List<Note>,
    val isLoading:Boolean,
    val error: String?
)
