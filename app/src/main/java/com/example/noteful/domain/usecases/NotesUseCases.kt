package com.example.noteful.domain.usecases

data class NotesUseCases(
    val getAllNotesUseCase: GetAllNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val getCategoryWithNotesUseCase: GetCategoryWithNotesUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase
)
