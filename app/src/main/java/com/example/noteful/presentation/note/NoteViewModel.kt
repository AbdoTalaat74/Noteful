package com.example.noteful.presentation.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteful.domain.model.Note
import com.example.noteful.domain.usecases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {




    private val _noteState = MutableStateFlow(
        NoteState(
            note = Note(
                text = "",
                categoryName = "",
            ),
            isLoading = false,
            error = null
        )
    )
    val noteState: StateFlow<NoteState> = _noteState

    fun updateCategoryName(categoryName: String) {
        val currentNote = _noteState.value.note
        _noteState.value = _noteState.value.copy(
            note = currentNote.copy(
                categoryName = categoryName
            )
        )
    }

    fun updateNoteText(text: String) {
        val currentNote = _noteState.value.note
        _noteState.value = _noteState.value.copy(
            note = currentNote.copy(
                text = text
            )
        )
    }

    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.upsertNoteUseCase.addNote(_noteState.value.note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getNoteById(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = notesUseCases.getNoteByIdUseCase.getNoteById(noteId)
                if (note != null) {
                    _noteState.value = _noteState.value.copy(
                        note = note
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

     fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.deleteNoteUseCase.deleteNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


