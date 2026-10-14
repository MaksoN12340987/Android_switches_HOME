package com.example.service.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TelegramDarkColors = darkColorScheme(
    primary = Color(0xFF5288C1),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2B5278),
    onPrimaryContainer = Color.White,

    background = Color(0xFF17212B),
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFF17212B),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF1E2A38),
    onSurfaceVariant = Color(0xFF6D7F8F),

    outline = Color(0xFF101921),

    error = Color(0xFFE53935),
    onError = Color.White
)

@Composable
fun SERVICETheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TelegramDarkColors,
        content = content
    )
}