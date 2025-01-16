package com.example.noteful.presentation.main

import com.example.noteful.domain.model.Category

data class CategoryState(
    val categories:List<Category>,
    val isLoading:Boolean = false,
    val error: String? = null
)
