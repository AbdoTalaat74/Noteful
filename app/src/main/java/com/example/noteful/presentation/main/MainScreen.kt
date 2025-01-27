package com.example.noteful.presentation.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.noteful.domain.model.Category
import com.example.noteful.domain.model.Note
import com.example.noteful.presentation.composables.CategoryCard
import com.example.noteful.presentation.composables.EditCategoriesMenu
import com.example.noteful.presentation.composables.NoteCard
import com.example.noteful.presentation.composables.SearchBar
import com.example.noteful.ui.theme.dimens

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    notesState: NotesState,
    categoryState: CategoryState,
    onNoteClick: (note: Note) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    onAddCategoryClick: () -> Unit = {},
    onDeleteCategory: () -> Unit,
    onEditCategoryName: () -> Unit,
    onError: () -> Unit,
    onDeleteCategoryNotes: () -> Unit,
    favoriteNotesState: NotesState,
) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(-1) }  // Track selected tab index
    val selectedCategoryName by rememberSaveable { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category(selectedCategoryName)) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (selectedTabIndex!= -2){
                FloatingActionButton(
                    onClick = {
                        onFloatingActionButtonClick()
                    },
                    containerColor = colorResource(R.color.selected_tab_container),
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note",
                        tint = if (isSystemInDarkTheme()) Color.Black else Color.White
                    )
                }
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier.weight(0.90f),
                    searchText = searchText,
                    readOnly = false,
                    onValueChange = {
                        onSearchTextChange(it)
                    },
                    onSearch = {
                        onSearch(it)
                    },
                )
                EditCategoriesMenu(
                    modifier = Modifier
                        .weight(0.1f)
                        .align(Alignment.CenterVertically),
                    onEditClick = {
                        onEditCategoryName()
                    },
                    onDeleteClick = {
                        if (selectedTabIndex == -1) {
                            onError()
                        } else {
                            onDeleteCategory()
                        }

                    },
                    onAddClick = {
                        onAddCategoryClick()
                    },
                    onDeleteCategoryNotes = {
                        if (selectedTabIndex == -1) {
                            onError()
                        } else {
                            selectedTabIndex = -1
                            onDeleteCategoryNotes()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

            ScrollableTabRow(
                edgePadding = 0.dp,
                divider = {},
                selectedTabIndex = selectedTabIndex,
                indicator = { /* Remove the default indicator by providing an empty Box */ },
                containerColor = MaterialTheme.colorScheme.surface, // Optional: customize background
                contentColor = MaterialTheme.colorScheme.primary // Optional: customize content color,
            ) {
                Tab(
                    selected = selectedTabIndex == -1,
                    onClick = {
                        selectedTabIndex = -1
                        onCategoryChanged("All")
                    },
                    content = {
                        CategoryCard(
                            category = Category(
                                categoryName = "All"
                            ),
                            containerColor = if (selectedTabIndex == -1) colorResource(R.color.selected_tab_container) else Color.Transparent,

                            )
                    },
                    selectedContentColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )

                categoryState.categories.forEachIndexed { index, category ->
                    val containerColor =
                        if (selectedTabIndex == index) colorResource(R.color.selected_tab_container) else Color.Transparent
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            onCategoryChanged(category.categoryName)
                            selectedCategory = category
                        },
                        content = {
                            CategoryCard(
                                category = category,
                                containerColor = containerColor,
                            )
                        },
                        selectedContentColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                if (favoriteNotesState.notes.isNotEmpty()) {
                    Tab(
                        selected = selectedTabIndex == -2,
                        onClick = {
                            selectedTabIndex = -2
                            onCategoryChanged("Favorite")
                        },
                        content = {
                            CategoryCard(
                                category = Category(
                                    categoryName = "Favorite"
                                ),
                                containerColor = if (selectedTabIndex == -2) colorResource(R.color.selected_tab_container) else Color.Transparent,

                                )
                        },
                        selectedContentColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    )
                }


            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notesState.notes.size) { index ->
                    NoteCard(
                        note = notesState.notes[index],
                        onClick = {
                            onNoteClick(notesState.notes[index])
                        }
                    )
                }
            }
        }
    }
}
