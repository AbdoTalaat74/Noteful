package com.example.noteful.domain.usecases

import com.example.noteful.domain.model.Category
import com.example.noteful.domain.repository.NotesRepository

class DeleteCategoryUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(category: Category) = repository.deleteCategory(category)

}