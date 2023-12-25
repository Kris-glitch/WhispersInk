package com.libraryrapp.whispersink.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    background = WhiteColor,
    surface = GreenColor,
    primary = MustardColor,
    secondary = GrayColor,
    tertiary = CreamColor,
    onBackground = WhiteColor,
    onPrimary = WhiteColor,
    onSecondary = BlackColor,
    onTertiary = BlackColor,
    onSurface = WhiteColor
)

@Composable
fun WhispersInkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}