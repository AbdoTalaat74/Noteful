package com.example.noteful.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.example.noteful.ui.theme.dimens

@Composable
fun ErrorDialog(
    onCancel: () -> Unit,
) {

    Dialog(onDismissRequest = onCancel) {
        Surface(

            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(MaterialTheme.dimens.small2)) {
                Text("You need to select a category to continue")
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small2)
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Cancel")
                    }
                }
            }

        }

    }
}