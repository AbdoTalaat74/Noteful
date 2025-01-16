package com.example.noteful.presentation.main

import androidx.lifecycle.ViewModel
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _notesState = MutableStateFlow(
        NoteState(
            notes = emptyList(), isLoading = false, error = null
        )
    )
    val notesState: StateFlow<NoteState> = _notesState


    private val _categoriesState = MutableStateFlow(
        CategoryState(
            categories = emptyList(), isLoading = false, error = null
        )
    )
    val categoriesState: StateFlow<CategoryState> = _categoriesState

    init {
        getNotes()
        getCategories()
    }

    private fun getNotes() {
        _notesState.value = _notesState.value.copy(
            notes = listOf(
                Note(
                    id = 1,
                    title = "Grocery List",
                    description = "Buy milk, eggs, bread, and butter for the weekend.",
                    categoryName = "Shopping"
                ),
                Note(
                    id = 2,
                    title = "Meeting Notes",
                    description = "Discuss the project timeline and assign tasks to team members. Review the budget plan for Q1.",
                    categoryName = "Work"
                ),
                Note(
                    id = 3,
                    title = "Workout Plan",
                    description = "Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.",
                    categoryName = "Fitness"
                ),
                Note(
                    id = 4,
                    title = "Book Recommendations",
                    description = "1. 'Atomic Habits' by James Clear\n2. 'The Alchemist' by Paulo Coelho\n3. 'Deep Work' by Cal Newport.",
                    categoryName = "Reading"
                ),
                Note(
                    id = 2,
                    title = "Meeting Notes",
                    description = "Discuss the project timeline and assign tasks to team members. Review the budget plan for Q1.",
                    categoryName = "Work"
                ),
                Note(
                    id = 3,
                    title = "Workout Plan",
                    description = "Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.",
                    categoryName = "Fitness"
                ),
                Note(
                    id = 4,
                    title = "Book Recommendations",
                    description = "1. 'Atomic Habits' by James Clear\n2. 'The Alchemist' by Paulo Coelho\n3. 'Deep Work' by Cal Newport.",
                    categoryName = "Reading"
                ),
                Note(
                    id = 5,
                    title = "Weekend Plans",
                    description = "Visit the park, have lunch at the new Italian restaurant, and watch a movie in the evening.",
                    categoryName = "Personal"
                ),
                Note(
                    id = 3,
                    title = "Workout Plan",
                    description = "Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.Morning run at 7 AM, followed by weightlifting. Focus on upper body strength exercises.",
                    categoryName = "Fitness"
                ),
                Note(
                    id = 5,
                    title = "Weekend Plans",
                    description = "Visit the park, have lunch at the new Italian restaurant, and watch a movie in the evening.",
                    categoryName = "Personal"
                ),
                Note(
                    id = 1,
                    title = "Grocery List",
                    description = "Buy milk, eggs, bread, and butter for the weekend.",
                    categoryName = "Shopping"
                ),
                Note(
                    id = 2,
                    title = "Meeting Notes",
                    description = "Discuss the project timeline and assign tasks to team members. Review the budget plan for Q1.",
                    categoryName = "Work"
                ),

                Note(
                    id = 4,
                    title = "Book Recommendations",
                    description = "1. 'Atomic Habits' by James Clear\n2. 'The Alchemist' by Paulo Coelho\n3. 'Deep Work' by Cal Newport.",
                    categoryName = "Reading"
                ),
                Note(
                    id = 5,
                    title = "Weekend Plans",
                    description = "Visit the park, have lunch at the new Italian restaurant, and watch a movie in the evening.",
                    categoryName = "Personal"
                ),
                Note(
                    id = 1,
                    title = "Grocery List",
                    description = "Buy milk, eggs, bread, and butter for the weekend.",
                    categoryName = "Shopping"
                ),


                )
        )
    }

    private fun getCategories(){
        _categoriesState.value = _categoriesState.value.copy(
            categories = listOf(
                Category(
                    categoryName = "All notes",
                    notes = emptyList()
                ),
                Category(
                    categoryName = "Important",
                    notes = emptyList()
                ),
                Category(
                    categoryName = "Lecture notes",
                    notes = emptyList()
                ),
                Category(
                    categoryName = "To-do list",
                    notes = emptyList()
                ),
                Category(
                    categoryName = "Shopping",
                    notes = emptyList()
                ),
            )
        )
    }
}