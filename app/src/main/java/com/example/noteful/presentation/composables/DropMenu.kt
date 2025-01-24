package com.example.noteful.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.noteful.ui.theme.dimens

@Composable
fun EditCategoriesMenu(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onAddClick: () -> Unit,
    onDeleteCategoryNotes: () -> Unit
) {
    var expandMenu by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        IconButton(
            onClick = { expandMenu = !expandMenu },
            modifier = Modifier.size(MaterialTheme.dimens.logoSize)
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "Menu")

            DropdownMenu(expanded = expandMenu, onDismissRequest = { expandMenu = !expandMenu }) {

                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onAddClick()
                    },
                    text = {
                        Text("Add Category")
                    },
                )
                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onEditClick()
                    },
                    text = { Text("Edit Category") },
                )
                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onDeleteClick()
                    },
                    text = { Text("Delete Category") },
                )
                DropdownMenuItem(
                    onClick = {
                        expandMenu = false
                        onDeleteCategoryNotes()
                    },
                    text = { Text("Delete Category with notes") },
                )


            }
        }
    }

}


