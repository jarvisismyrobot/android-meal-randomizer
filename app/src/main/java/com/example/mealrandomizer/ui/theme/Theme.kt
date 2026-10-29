package com.example.mealrandomizer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Warm "home kitchen" palette: paper background, espresso ink, persimmon accent.
 * Designed to feel like food, not like a template.
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFBE4D0C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDBCA),
    onPrimaryContainer = Color(0xFF3A1C00),
    secondary = Color(0xFF6E5C49),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF6E3D2),
    onSecondaryContainer = Color(0xFF2A1E12),
    tertiary = Color(0xFF4F6136),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD3E9AE),
    onTertiaryContainer = Color(0xFF121F00),
    error = Color(0xFFBA1A1A),
    background = Color(0xFFFAF5EC),
    onBackground = Color(0xFF201A13),
    surface = Color(0xFFFFFDF7),
    onSurface = Color(0xFF201A13),
    surfaceVariant = Color(0xFFF1E6D2),
    onSurfaceVariant = Color(0xFF504539),
    surfaceContainerLow = Color(0xFFF7F1E4),
    outline = Color(0xFFD9CDB4),
    outlineVariant = Color(0xFFE8DECB),
    inverseSurface = Color(0xFF2F2A23),
    inverseOnSurface = Color(0xFFF7F1E4)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB690),
    onPrimary = Color(0xFF552000),
    primaryContainer = Color(0xFF8A3C07),
    onPrimaryContainer = Color(0xFFFFDBCA),
    secondary = Color(0xFFD8C2A9),
    onSecondary = Color(0xFF3B2D1F),
    secondaryContainer = Color(0xFF4E4136),
    onSecondaryContainer = Color(0xFFF6E3D2),
    tertiary = Color(0xFFB8CE8D),
    onTertiary = Color(0xFF263500),
    tertiaryContainer = Color(0xFF374A1F),
    onTertiaryContainer = Color(0xFFD3E9AE),
    error = Color(0xFFFFB4AB),
    background = Color(0xFF161210),
    onBackground = Color(0xFFEDE1D2),
    surface = Color(0xFF1D1814),
    onSurface = Color(0xFFEDE1D2),
    surfaceVariant = Color(0xFF4E443A),
    onSurfaceVariant = Color(0xFFD2C4B2),
    surfaceContainerLow = Color(0xFF221C17),
    outline = Color(0xFF7D7468),
    outlineVariant = Color(0xFF4E443A),
    inverseSurface = Color(0xFFEDE1D2),
    inverseOnSurface = Color(0xFF201A13)
)

/** Warm tint colors for the three daily meals (work on light and dark). */
object MealTint {
    val breakfast = Color(0xFFD9A404)
    val breakfastBg = Color(0xFFFFF3D6)
    val lunch = Color(0xFFC24E0C)
    val lunchBg = Color(0xFFFFE8D9)
    val dinner = Color(0xFF5B6FD6)
    val dinnerBg = Color(0xFFE4E9FB)
}

/** Deep espresso used for the hero card in both themes. */
val Espresso = Color(0xFF2A2118)
val EspressoSoft = Color(0xFFF5B47E)

/** Backwards-compatible alias for screens not yet redesigned. */
@Deprecated("Use MealTint instead", ReplaceWith("MealTint"))
object FoodColors {
    val breakfast = MealTint.breakfastBg
    val lunch = MealTint.lunchBg
    val dinner = MealTint.dinnerBg
    val vegetarian = Color(0xFFE8F5E8)
    val quickMeal = Color(0xFFFFF8E1)
}

@Composable
fun MealRandomizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
