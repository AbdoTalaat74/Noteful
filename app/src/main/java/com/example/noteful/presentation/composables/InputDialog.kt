package com.example.noteful.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.example.noteful.ui.theme.dimens

@Composable
fun InputDialog(
    initialText: String = "",
    onCancel: () -> Unit,
    onDone: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small2)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.dimens.small2),
                    singleLine = true,
                    placeholder = { Text("Enter Category Name...") }
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        if (text.isNotEmpty()){
                            onDone(text)

                        }
                    }) {
                        Text("Done")
                    }
                }
            }
        }
    }
}