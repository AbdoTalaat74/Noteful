package com.example.noteful

import android.os.Bundle
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.presentation.composables.DeleteCategoryDialog
import com.example.noteful.presentation.composables.ErrorDialog
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
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(dynamicColor = false) {

                NotesAroundApp()

            }
        }
    }

    @Composable
    fun NotesAroundApp() {
        var showCategoryNameDialog by remember { mutableStateOf(false) }
        var showDeleteCategoryDialog by remember { mutableStateOf(false) }
        var showDeleteCategoryWithNotesDialog by remember { mutableStateOf(false) }
        var showErrorDialog by remember { mutableStateOf(false) }
        var showUpdateCategoryDialog by remember { mutableStateOf(false) }
        val navController = rememberNavController()
        val mainViewModel: MainViewModel = hiltViewModel()
        val selectedCategory by mainViewModel.categorySelected.collectAsState()
        val favoriteNotesState by mainViewModel.favoriteNotesState.collectAsState()

        if (showCategoryNameDialog) {
            InputDialog(
                onCancel = {
                    showCategoryNameDialog = false
                },
                onDone = { input ->
                    mainViewModel.addCategory(Category(categoryName = input))
                    showCategoryNameDialog = false
                }
            )
        }

        if (showErrorDialog) {
            ErrorDialog(
                onCancel = {
                    showErrorDialog = false
                }
            )
        }
        if (showDeleteCategoryDialog) {
            DeleteCategoryDialog(
                categoryName = selectedCategory,
                onCancel = {
                    showDeleteCategoryDialog = false
                },
                onDelete = {
                    mainViewModel.deleteCategory()
                    showDeleteCategoryDialog = false
                }
            )
        }
        if (showUpdateCategoryDialog) {
            InputDialog(
                onCancel = {
                    showUpdateCategoryDialog = false
                },
                onDone = { newName ->
                    showUpdateCategoryDialog = false
                    mainViewModel.updateCategory(newName)
                }
            )
        }
        if (showDeleteCategoryWithNotesDialog) {
            DeleteCategoryDialog(
                categoryName = selectedCategory,
                onCancel = {
                    showDeleteCategoryWithNotesDialog = false
                },
                onDelete = {
                    mainViewModel.deleteCategoryWithNotes()
                    showDeleteCategoryWithNotesDialog = false
                },
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


                val navBackStackEntry = navController.currentBackStackEntry
                navBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("refresh_notes")
                    ?.observeForever { shouldRefresh ->
                        if (shouldRefresh == true) {
                            mainViewModel.getFavoriteNotes()
                            mainViewModel.refreshNotes()
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
                    notesState = noteState,
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
                        showCategoryNameDialog = true
                    },
                    onDeleteCategory = {
                        showDeleteCategoryDialog = true
                    },
                    onEditCategoryName = {
                        showUpdateCategoryDialog = true
                    },
                    onError = {
                        showErrorDialog = true

                    },
                    onDeleteCategoryNotes = {
                        showDeleteCategoryWithNotesDialog = true
                    },
                    favoriteNotesState = favoriteNotesState
                )

            }
            composable<NoteScreenRout> {
                val noteId = it.toRoute<NoteScreenRout>().noteId
                val isNewNote = it.toRoute<NoteScreenRout>().noteText.isEmpty()
                val noteCategoryName = it.toRoute<NoteScreenRout>().noteCategoryName
                val noteViewModel: NoteViewModel = hiltViewModel()
                val noteState by noteViewModel.noteState.collectAsState()
                if (isNewNote) {
                    noteViewModel.updateCategoryName(noteCategoryName)
                }
                NoteScreen(
                    modifier = Modifier.fillMaxSize(),
                    noteId = noteId,
                    isNewNote = isNewNote,
                    onBackClick = {
                        if (selectedCategory == "Favorite"){
                            mainViewModel.getFavoriteNotes()
                        }
                        if (noteState.note.text == "") {
                            noteViewModel.deleteNote(noteState.note)
                        } else {
                            noteViewModel.saveNote()
                        }
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "refresh_notes",
                            true
                        )
                        navController.navigateUp()
                    },
                    onFavoriteClick = {
                        noteViewModel.changeNoteFavoriteState()

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

