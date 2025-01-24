package com.example.noteful.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteful.domain.model.Category
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


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }


    private val _categorySelected = MutableStateFlow("All")
    val categorySelected: StateFlow<String> = _categorySelected

    fun updateCategorySelected(newSelectedCategory: String) {
        _categorySelected.value = newSelectedCategory
        if (newSelectedCategory == "All") {
            getNotes()
        } else {
            getNotesByCategory(newSelectedCategory)
        }
    }

    private fun getNotesByCategory(selectedCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val notes =
                    notesUseCases.getCategoryWithNotesUseCase(categoryName = selectedCategory)
                notes.forEach {
                    _notesState.value = _notesState.value.copy(
                        notes = it.notes
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private val _notesState = MutableStateFlow(
        NotesState(
            notes = emptyList(), isLoading = false, error = null
        )
    )
    val notesState: StateFlow<NotesState> = _notesState


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


    fun searchNote(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val notes = notesUseCases.searchNoteUseCase.searchNote(query)
                _notesState.value = _notesState.value.copy(
                    notes = notes
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSearchEmpty() {
        getNotes()
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

    fun refreshNotes() {
        if (_categorySelected.value == "All") {
            getNotes()
        } else {
            getNotesByCategory(_categorySelected.value)
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
                getCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun deleteCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.deleteCategoryUseCase(Category(_categorySelected.value))
                getCategories()
                getNotes()


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateCategory(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.updateCategoryUseCase(_categorySelected.value, newName)
                getCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteCategoryWithNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.deleteCategoryWithNotesUseCase(Category(_categorySelected.value))
                getCategories()
                getNotes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}