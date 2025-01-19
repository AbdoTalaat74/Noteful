package com.example.noteful.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noteful.domain.model.Category
import com.example.noteful.ui.theme.dimens

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    containerColor: Color
) {
    Card(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.small2, vertical = MaterialTheme.dimens.small1)
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                shape =RoundedCornerShape(MaterialTheme.dimens.small1)
            )
        ,
        shape = RoundedCornerShape(MaterialTheme.dimens.small1),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    )
    {
        Text(
            text = category.categoryName,
            maxLines = 1, // Limit to one line,,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier= Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.small1)
                .padding(horizontal = MaterialTheme.dimens.small1))

    }

}