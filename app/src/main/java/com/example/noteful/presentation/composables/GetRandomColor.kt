package com.example.noteful.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.R

@Composable
fun getRandomColor() : Color {
    val context = LocalContext.current
    val colorArray = context.resources.getIntArray(R.array.more_light_colors)

    // Convert the int array to a List of Color objects
    val lightColors = colorArray.map { Color(it) }

    // Randomly pick a color from the list
    return lightColors.random()
}