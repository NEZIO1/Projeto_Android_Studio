package br.com.fiap.challenge.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary       = Primary,
    onPrimary     = OnPrimary,
    secondary     = Secondary,
    onSecondary   = OnSecondary,
    background    = Background,
    onBackground  = OnBackground,
    surface       = Surface,
    onSurface     = OnSurface,
    tertiary      = Tertiary,
    error         = ErrorColor
)

@Composable
fun SofttekTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography  = Typography,
        shapes      = Shapes,
        content     = content
    )
}

