package com.dve.sari.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = pokemon_dark_primary,
    onPrimary = pokemon_dark_onPrimary,
    primaryContainer = pokemon_dark_primaryContainer,
    onPrimaryContainer = pokemon_dark_onPrimaryContainer,
    secondary = pokemon_dark_secondary,
    onSecondary = pokemon_dark_onSecondary,
    secondaryContainer = pokemon_dark_secondaryContainer,
    onSecondaryContainer = pokemon_dark_onSecondaryContainer,
    tertiary = pokemon_dark_tertiary,
    onTertiary = pokemon_dark_onTertiary,
    tertiaryContainer = pokemon_dark_tertiaryContainer,
    onTertiaryContainer = pokemon_dark_onTertiaryContainer,
    error = pokemon_dark_error,
    errorContainer = pokemon_dark_errorContainer,
    onError = pokemon_dark_onError,
    onErrorContainer = pokemon_dark_onErrorContainer,
    background = pokemon_dark_background,
    onBackground = pokemon_dark_onBackground,
    surface = pokemon_dark_surface,
    onSurface = pokemon_dark_onSurface,
    surfaceVariant = pokemon_dark_surfaceVariant,
    onSurfaceVariant = pokemon_dark_onSurfaceVariant,
    outline = pokemon_dark_outline,
    inverseOnSurface = pokemon_dark_inverseOnSurface,
    inverseSurface = pokemon_dark_inverseSurface,
    inversePrimary = pokemon_dark_inversePrimary,
    surfaceTint = pokemon_dark_surfaceTint,
    outlineVariant = pokemon_dark_outlineVariant,
    scrim = pokemon_dark_scrim,
)

private val LightColorScheme = lightColorScheme(
    primary = pokemon_light_primary,
    onPrimary = pokemon_light_onPrimary,
    primaryContainer = pokemon_light_primaryContainer,
    onPrimaryContainer = pokemon_light_onPrimaryContainer,
    secondary = pokemon_light_secondary,
    onSecondary = pokemon_light_onSecondary,
    secondaryContainer = pokemon_light_secondaryContainer,
    onSecondaryContainer = pokemon_light_onSecondaryContainer,
    tertiary = pokemon_light_tertiary,
    onTertiary = pokemon_light_onTertiary,
    tertiaryContainer = pokemon_light_tertiaryContainer,
    onTertiaryContainer = pokemon_light_onTertiaryContainer,
    error = pokemon_light_error,
    errorContainer = pokemon_light_errorContainer,
    onError = pokemon_light_onError,
    onErrorContainer = pokemon_light_onErrorContainer,
    background = pokemon_light_background,
    onBackground = pokemon_light_onBackground,
    surface = pokemon_light_surface,
    onSurface = pokemon_light_onSurface,
    surfaceVariant = pokemon_light_surfaceVariant,
    onSurfaceVariant = pokemon_light_onSurfaceVariant,
    outline = pokemon_light_outline,
    inverseOnSurface = pokemon_light_inverseOnSurface,
    inverseSurface = pokemon_light_inverseSurface,
    inversePrimary = pokemon_light_inversePrimary,
    surfaceTint = pokemon_light_surfaceTint,
    outlineVariant = pokemon_light_outlineVariant,
    scrim = pokemon_light_scrim,
)

@Composable
fun PokeXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }


        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
