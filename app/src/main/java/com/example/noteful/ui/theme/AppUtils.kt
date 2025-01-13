package com.example.noteful.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun AppUtils(
    dimens: Dimens,
    content: @Composable () -> Unit
) {
    val appDimens = remember {
        dimens
    }

    CompositionLocalProvider(LocalAppDimens provides appDimens) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf {
    CompactDimens
}

