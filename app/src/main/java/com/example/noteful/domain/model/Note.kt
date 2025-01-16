package com.example.noteful.domain.model



data class Note(
    val id: Int,
    val title:String,
    val description: String,
    val categoryName: String
)
