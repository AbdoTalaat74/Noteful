package com.example.noteful.presentation.note

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.noteful.HeaderBodyTransformation
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import com.example.noteful.ui.theme.dimens

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteId: Int,
    isNewNote: Boolean = false,
    onBackClick: (Boolean) -> Unit,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val noteViewModel: NoteViewModel = hiltViewModel()
    if (!isNewNote) {
        noteViewModel.getNoteById(noteId)
    }
    val noteState by noteViewModel.noteState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            NoteTopAppBar(
                isFavorite = noteState.note.isFavorite,
                onBackClick = {
                    onBackClick(it)
                },
                onClipboardClick = {
                    clipboardManager.setText(AnnotatedString(noteState.note.text))
                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                },
                onFavoriteClick = {
                    onFavoriteClick()
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                BasicTextField(
                    value = noteState.note.text,
                    onValueChange = {
                        noteViewModel.updateNoteText(it)
                    },

                    visualTransformation = HeaderBodyTransformation(),
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.dimens.small1)
                )
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteTopAppBar(
    isFavorite: Boolean,
    onBackClick: (Boolean) -> Unit,
    onClipboardClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button at the end
                IconButton(onClick = { onBackClick(isFavorite) }) {
                    Icon(
                        painter = painterResource(R.drawable.backarrow), // Replace with your back icon
                        contentDescription = "Back Icon"
                    )
                }

                // Icons at the start
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onClipboardClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.clipboardtext), // Replace with your icon
                            contentDescription = "Copy to Clipboard"
                        )
                    }
                    IconButton(onClick = { onFavoriteClick() }) {
                        Icon(
                            painter = if (isFavorite) painterResource(R.drawable.filled_heart) else painterResource(
                                R.drawable.heart
                            ), // Replace with your icon
                            contentDescription = "Add to Favorites"
                        )
                    }
                    IconButton(onClick = { /* Handle third icon click */ }) {
                        Icon(
                            painter = painterResource(R.drawable.directboxsend), // Replace with your icon
                            contentDescription = "Share"
                        )
                    }
                }
            }
        },
    )
}
