package com.example.noteful

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.presentation.composables.InputDialog
import com.example.noteful.presentation.main.MainScreen
import com.example.noteful.presentation.main.MainViewModel
import com.example.noteful.presentation.note.NoteScreen
import com.example.noteful.presentation.note.NoteViewModel
import com.example.noteful.ui.theme.MyApplicationTheme
import com.example.noteful.ui.theme.dimens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(dynamicColor = false) {

                NotesAroundApp()

            }

        }
    }

    @Composable
    fun NotesAroundApp() {
        var showDialog by remember { mutableStateOf(false) }
        val navController = rememberNavController()
        val mainViewModel: MainViewModel = hiltViewModel()
        Log.e("DialogInputLig",showDialog.toString())

        if (showDialog){
            InputDialog(
                title = "Enter Category Name",
                onCancel = {
                    showDialog = false
                           },
                onDone = { input ->
                    mainViewModel.addCategory(Category(categoryName = input))
                    showDialog = false
                }
            )
        }


        NavHost(
            navController = navController,
            startDestination = MainScreenRout
        ) {
            composable<MainScreenRout> {
                val noteState by mainViewModel.notesState.collectAsState()
                val categoryState by mainViewModel.categoriesState.collectAsState()
                val query by mainViewModel.query.collectAsState()
                val selectedCategory by mainViewModel.categorySelected.collectAsState()


                val navBackStackEntry = navController.currentBackStackEntry
                navBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("refresh_notes")?.observeForever { shouldRefresh ->
                    if (shouldRefresh == true) {
                        Log.e("navBackStackEntryLog","Refreshed")
                        mainViewModel.refreshNotes()
                        // Reset the value to prevent repeated refreshes
                        navBackStackEntry.savedStateHandle["refresh_notes"] = false
                    }
                }

                MainScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(
                            horizontal = MaterialTheme.dimens.small1,
                            vertical = MaterialTheme.dimens.small2
                        ),
                    noteState = noteState,
                    categoryState = categoryState,
                    searchText = query,
                    onNoteClick = {
                        navController.navigate(
                            NoteScreenRout(
                                it.id,
                                it.text,
                                it.categoryName
                            )
                        )
                    },
                    onSearchTextChange = {
                        if (it.isEmpty()) {
                            mainViewModel.onSearchEmpty()
                            mainViewModel.updateQuery(it)
                        } else {
                            mainViewModel.updateQuery(it)
                            mainViewModel.searchNote(it)
                        }
                    },
                    onSearch = {
                        if (it.isNotEmpty()) {
                            mainViewModel.searchNote(it)
                        }
                    },
                    onCategoryChanged = {
                        Log.e("updateCategorySelectedLog",it)
                        mainViewModel.updateCategorySelected(it)
                    },
                    onFloatingActionButtonClick = {
                        val note = Note(
                            text = "",
                            categoryName = selectedCategory
                        )
                        navController.navigate(
                            NoteScreenRout(
                                noteId = note.id,
                                noteText = note.text,
                                noteCategoryName = selectedCategory
                            )
                        )
                    },
                    onAddCategoryClick = {
                        showDialog = true
                    }


                )

            }
            composable<NoteScreenRout> {
                val noteId = it.toRoute<NoteScreenRout>().noteId
                val isNewNote = it.toRoute<NoteScreenRout>().noteText.isEmpty()
                val noteCategoryName = it.toRoute<NoteScreenRout>().noteCategoryName
                val noteViewModel:NoteViewModel = hiltViewModel()
                val noteState by noteViewModel.noteState.collectAsState()
                if (isNewNote){
                    noteViewModel.updateCategoryName(noteCategoryName)
                    Log.e("updateCategoryName",noteCategoryName)
                }
                NoteScreen(
                    noteId = noteId,
                    isNewNote = isNewNote,
                    onBackClick = {
                        if (noteState.note.text == ""){
                            noteViewModel.deleteNote(noteState.note)
                        }else{
                            noteViewModel.saveNote()
                            Log.e("onBackClick", noteState.note.text)
                        }
                        navController.previousBackStackEntry?.savedStateHandle?.set("refresh_notes", true)
                        navController.navigateUp()
                    }
                )
            }
        }

    }

    @Serializable
    object MainScreenRout

    @Serializable
    data class NoteScreenRout(
        val noteId: Int,
        val noteText: String,
        val noteCategoryName: String
    )
}

