package com.example.noteful.presentation.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.noteful.presentation.composables.CategoryCard
import com.example.noteful.presentation.composables.NoteCard
import com.example.noteful.presentation.composables.SearchBar
import com.example.noteful.ui.theme.dimens

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    noteState: NoteState,
    categoryState: CategoryState
) {

    var selectedTabIndex by remember { mutableIntStateOf(0) } // Track selected tab index

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle FAB click */ },
                containerColor = colorResource(R.color.selected_tab_container),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    tint = if(isSystemInDarkTheme()) Color.Black else Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                text = "Search For Notes",
                readOnly = false,
                onValueChange = {},
                onSearch = {},
                onClick = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

            ScrollableTabRow(
                edgePadding = 0.dp,
                divider = {},
                selectedTabIndex = selectedTabIndex,
                indicator = { /* Remove the default indicator by providing an empty Box */ },
                containerColor = MaterialTheme.colorScheme.surface, // Optional: customize background
                contentColor = MaterialTheme.colorScheme.primary // Optional: customize content color
            ) {

                categoryState.categories.forEachIndexed { index, category ->
                    val containerColor =
                        if (selectedTabIndex == index) colorResource(R.color.selected_tab_container) else Color.Transparent
                    Tab(
                        modifier = Modifier.padding(MaterialTheme.dimens.small1),
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        content = { CategoryCard(category = category, containerColor = containerColor) },
                        selectedContentColor = if(isSystemInDarkTheme()) Color.Black else Color.White,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(noteState.notes.size) { index ->
                    NoteCard(
                        note = noteState.notes[index],
                        onClick = {}
                    )
                }
            }
        }
    }


}
