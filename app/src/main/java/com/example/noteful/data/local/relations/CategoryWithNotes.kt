package com.example.noteful.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note

data class CategoryWithNotes(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val notes:List<Note>
)
