package com.example.noteful

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp



class HeaderBodyTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val textLines = text.text.split("\n")
        val transformedText = buildAnnotatedString {
            textLines.forEachIndexed { index, line ->
                if (index == 0) {
                    // First line: Bold and large
                    withStyle(
                        style = SpanStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(line)
                    }
                } else {
                    append("\n")
                    // Subsequent lines: Body style
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp
                        )
                    ) {
                        append(line)
                    }
                }
            }
        }
        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}