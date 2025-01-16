package com.example.noteful.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.domain.usecases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
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

    private fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.addNoteUseCase.addNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val notes = notesUseCases.getAllNotesUseCase()
                _notesState.value = _notesState.value.copy(
                    notes = notes
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = notesUseCases.getCategoriesUseCase()
                _categoriesState.value = _categoriesState.value.copy(
                    categories = categories
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.addCategoryUseCase.addCategory(category)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}