package com.example.noteful.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.myapplication.R
import com.example.noteful.ui.theme.dimens


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    readOnly: Boolean,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.dimens.small1,
                top = MaterialTheme.dimens.small1,
                bottom = MaterialTheme.dimens.small1
            ),
        value = searchText,
        onValueChange = onValueChange,
        readOnly = readOnly,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .size(MaterialTheme.dimens.iconSize)
                    .clickable {
                        onSearch(searchText)
                    },
                tint = colorResource(id = R.color.body)
            )
        },
        placeholder = {
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.body)
            )
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.search_bar_background_color),
            unfocusedContainerColor = colorResource(R.color.search_bar_background_color),
            focusedTextColor = colorResource(R.color.body),
            unfocusedTextColor = colorResource(R.color.body),
            cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchText)
            }
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        interactionSource = interactionSource
    )

}


