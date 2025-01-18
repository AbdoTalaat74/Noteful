package com.example.noteful.presentation.note

import android.util.Log
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
):ViewModel() {


    private val _noteId = MutableStateFlow<Int>(0)

    fun updateNoteId(id:Int) {
        _noteId.value = id
    }

    private val _noteState = MutableStateFlow(
        NoteState(
            note = Note(
                text = "",
                categoryName ="Important",
            ),
            isLoading = false,
            error = null
        )
    )
    val noteState:StateFlow<NoteState> = _noteState



    init {
        viewModelScope.launch {
            _noteId.collect { id ->
                Log.e("NoteIdLog", "Note ID updated: $id")

                getNoteById(_noteId.value)

            }

        }

    }


    fun updateNoteText(text: String) {
        _noteState.value = _noteState.value.copy(
            note = Note(
                text = text,
                categoryName = _noteState.value.note.categoryName,
            )
        )
    }

    fun saveNote(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.upsertNoteUseCase.addNote(_noteState.value.note)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getNoteById(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (_noteId.value != 0 ){
                    val note = notesUseCases.getNoteByIdUseCase.getNoteById(id)
                    if (note != null){
                        _noteState.value = _noteState.value.copy(
                            note = note
                        )
                    }else{
                        _noteState.value = _noteState.value.copy(
                            note = Note(
                                text = "",
                                categoryName = "All Notes",
                            )
                        )
                    }
                }else{
                    _noteState.value = _noteState.value.copy(
                        note = Note(
                            text = "",
                            categoryName = "All Notes",
                        )
                    )
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}