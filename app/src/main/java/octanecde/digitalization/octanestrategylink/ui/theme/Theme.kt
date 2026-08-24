package octanecde.digitalization.octanestrategylink.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OctaneAccent,
    secondary = OctanePrimary,
    background = OctanePrimaryDark,
    surface = OctanePrimary,
    onPrimary = OctanePrimaryDark,
    onSurface = OctaneOnPrimary,
)

private val LightColorScheme = lightColorScheme(
    primary = OctanePrimary,
    secondary = OctaneAccent,
    tertiary = OctaneSuccess,
    background = OctaneBackground,
    surface = OctaneSurface,
    onPrimary = OctaneOnPrimary,
    onSecondary = OctanePrimaryDark,
    onBackground = OctaneOnSurface,
    onSurface = OctaneOnSurface,
    onSurfaceVariant = OctaneMuted,
    outline = OctaneBorder,
    error = OctaneWarning,
)

@Composable
fun ServiceSkeletonTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        content = content,
    )
}
