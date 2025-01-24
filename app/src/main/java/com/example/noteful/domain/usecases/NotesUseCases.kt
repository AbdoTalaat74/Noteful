package com.example.noteful.domain.usecases

data class NotesUseCases(
    val getAllNotesUseCase: GetAllNotesUseCase,
    val upsertNoteUseCase: UpsertNoteUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    val getCategoryWithNotesUseCase: GetCategoryWithNotesUseCase,
    val getCategoriesUseCase: GetCategoriesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val searchNoteUseCase: SearchNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
    val deleteCategoryWithNotesUseCase: DeleteCategoryWithNotesUseCase
)
