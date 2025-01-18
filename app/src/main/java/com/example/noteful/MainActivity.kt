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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainScreenRout
        ) {
            composable<MainScreenRout> {
                val mainViewModel: MainViewModel = hiltViewModel()
                val noteState by mainViewModel.notesState.collectAsState()
                val categoryState by mainViewModel.categoriesState.collectAsState()
                val query by mainViewModel.query.collectAsState()
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
                                noteId = it.id,
                            )
                        )
                    },
                    onSearchTextChange = {
                        if (it.isEmpty()){
                            mainViewModel.onSearchEmpty()
                            mainViewModel.updateQuery(it)
                        }else{
                            mainViewModel.updateQuery(it)
                            mainViewModel.searchNote(it)
                        }
                    },
                    onSearch = {
                        if (it.isNotEmpty()){
                            mainViewModel.searchNote(it)
                        }
                    },
                    onCategoryChanged = {
                        mainViewModel.updateCategorySelected(it)
                    }
                )
            }

            composable<NoteScreenRout> {
                val noteViewModel: NoteViewModel = hiltViewModel()
                val noteId = it.toRoute<NoteScreenRout>().noteId
                noteViewModel.updateNoteId(noteId)

                val noteState by noteViewModel.noteState.collectAsState()

                NoteScreen(
                    noteState = noteState,
                    onBackClick = {
                        noteViewModel.saveNote()
                        navController.navigateUp()
                    },
                    onClipboardClick = {},
                    onFavoriteClick = {}
                )

            }
        }
    }

    @Serializable
    object MainScreenRout

    @Serializable
    data class NoteScreenRout(
        val noteId: Int,
    )
}

