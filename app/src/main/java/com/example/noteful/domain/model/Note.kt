package com.example.noteful.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val categoryName: String,
    val isFavorite:Boolean = false
)
