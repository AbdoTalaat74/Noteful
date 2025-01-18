package com.example.noteful.presentation.note

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.noteful.HeaderBodyTransformation

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteState: NoteState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onClipboardClick: () -> Unit
) {

    val noteViewModel: NoteViewModel = hiltViewModel()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            NoteTopAppBar(
                onBackClick = {
                    onBackClick()
                },
                onClipboardClick = {
                    onClipboardClick()
                },
                onFavoriteClick = {
                    onFavoriteClick()
                }
            )
        }
    ) { paddingValues ->

        Log.e("NoteStateLog", noteState.note.text)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = noteState.note.text,
                onValueChange = {
                    noteViewModel.updateNoteText(it)
                },
                visualTransformation = HeaderBodyTransformation(),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteTopAppBar(
    onBackClick: () -> Unit,
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
                IconButton(onClick = { onBackClick() }) {
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
                            contentDescription = "Home Icon"
                        )
                    }
                    IconButton(onClick = { onFavoriteClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.heart), // Replace with your icon
                            contentDescription = "Search Icon"
                        )
                    }
                    IconButton(onClick = { /* Handle third icon click */ }) {
                        Icon(
                            painter = painterResource(R.drawable.directboxsend), // Replace with your icon
                            contentDescription = "Settings Icon"
                        )
                    }
                }
            }
        },
    )
}

