package com.example.noteful.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.noteful.domain.model.Note
import com.example.noteful.ui.theme.dimens

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (note: Note) -> Unit
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.dimens.small3),
        modifier = modifier
            .defaultMinSize(200.dp)
            .padding(MaterialTheme.dimens.small1)
            .clickable {
                onClick(note)
            },
        colors = CardDefaults.cardColors(containerColor = getRandomColor())


    ) {
        val parts =
            note.text.split("\n", limit = 2) // Split text into two parts at the first newline
        val beforeNewline = parts.getOrNull(0) ?: "" // Text before the newline
        val afterNewline = parts.getOrNull(1) ?: "" // Text after the newline

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.small2),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                    ) {
                        append(beforeNewline)
                    }

                    if (afterNewline.isNotEmpty()) {
                        append("\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )
                    ) {
                        append(afterNewline)
                    }
                },
                modifier = Modifier.padding(MaterialTheme.dimens.small1),
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
            )
        }

    }
}