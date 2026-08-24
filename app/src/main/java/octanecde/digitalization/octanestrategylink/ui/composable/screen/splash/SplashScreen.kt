package octanecde.digitalization.octanestrategylink.ui.composable.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import octanecde.digitalization.octanestrategylink.R
import octanecde.digitalization.octanestrategylink.ui.theme.OctaneAccent
import octanecde.digitalization.octanestrategylink.ui.theme.OctanePrimary
import octanecde.digitalization.octanestrategylink.ui.viewmodel.PPTKNSplashVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: PPTKNSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboarded by viewModel.onboardedState.collectAsStateWithLifecycle()

    SplashScreenContent(modifier = modifier)

    LaunchedEffect(onboarded) {
        delay(1500)
        if (onboarded) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    var started by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (started) 1f else 0.8f,
        animationSpec = tween(800),
        label = "splashScale",
    )

    LaunchedEffect(Unit) {
        started = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(OctanePrimary, OctaneAccent))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = "Octane Strategy Link",
            modifier = Modifier
                .size(156.dp)
                .scale(scale),
        )
        androidx.compose.material3.Text(
            text = "Octane Strategy Link",
            color = Color.White,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
        )
    }
}
