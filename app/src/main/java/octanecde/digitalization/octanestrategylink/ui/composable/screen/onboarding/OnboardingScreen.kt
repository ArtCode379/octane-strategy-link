package octanecde.digitalization.octanestrategylink.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import octanecde.digitalization.octanestrategylink.R
import octanecde.digitalization.octanestrategylink.ui.viewmodel.PPTKNOnboardingVM
import org.koin.androidx.compose.koinViewModel

private data class OnboardingContent(
    val title: String,
    val description: String,
    val icon: ImageVector,
    @DrawableRes val image: Int,
)

private val pages = listOf(
    OnboardingContent(
        "Build a strategy that delivers",
        "Connect technology investment to measurable business outcomes with an independent, practical roadmap.",
        Icons.Default.Timeline,
        R.drawable.service_1,
    ),
    OnboardingContent(
        "Secure every critical layer",
        "Understand exposure across identity, infrastructure, applications, and data before it becomes disruption.",
        Icons.Default.Security,
        R.drawable.service_2,
    ),
    OnboardingContent(
        "Modernise with confidence",
        "Plan cloud, data, and process change around value, resilience, and the people who make it work.",
        Icons.Default.Cloud,
        R.drawable.service_3,
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: PPTKNOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val complete by viewModel.onboardingSetState.collectAsState()

    LaunchedEffect(complete) {
        if (complete) {
            onNavigateToHomeScreen()
        }
    }

    OnboardingScreenContent(
        modifier = modifier,
        onOnboardingComplete = viewModel::setOnboarded,
    )
}

@Composable
private fun OnboardingScreenContent(
    modifier: Modifier = Modifier,
    onOnboardingComplete: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp),
                )
                Spacer(Modifier.height(22.dp))
                Text(page.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                Text(
                    text = page.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(Modifier.height(30.dp))
                Image(
                    painter = painterResource(page.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp, 120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pages.indices.forEach { index ->
                Box(
                    Modifier
                        .size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                MaterialTheme.colorScheme.outline
                            },
                        ),
                )
            }
        }
        Spacer(Modifier.height(22.dp))
        Button(
            onClick = {
                if (pagerState.currentPage == pages.lastIndex) {
                    onOnboardingComplete()
                } else {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Continue")
        }
    }
}
