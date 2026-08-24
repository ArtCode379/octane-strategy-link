package octanecde.digitalization.octanestrategylink.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import octanecde.digitalization.octanestrategylink.data.model.ServiceModel
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNContentWrapper
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.theme.OctaneAccent
import octanecde.digitalization.octanestrategylink.ui.theme.OctanePrimary
import octanecde.digitalization.octanestrategylink.ui.viewmodel.ServiceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val servicesState by viewModel.servicesState.collectAsState()
    PPTKNContentWrapper(
        dataState = servicesState,
        dataPopulated = {
            ServicesPopulated(
                services = (servicesState as DataUiState.Populated).data,
                modifier = modifier,
                onNavigateToServiceDetails = onNavigateToServiceDetails,
            )
        },
        dataEmpty = {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("New advisory services are coming soon.")
            }
        },
    )
}

@Composable
private fun ServicesPopulated(
    services: List<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text("Technology decisions, made clearer.", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Independent strategy, assurance, and transformation expertise.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item {
            NextAvailableBanner()
        }
        item {
            Text(
                text = "Explore by capability",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
            )
        }
        item {
            CategoryRow()
        }
        item {
            Text(
                text = "Advisory services",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
            )
        }
        items(services, key = { it.id }) { service ->
            ServiceCard(
                service = service,
                onClick = { onNavigateToServiceDetails(service.id) },
            )
        }
        item {
            InsightSection(
                title = "Selected outcomes",
                entries = listOf(
                    "Cloud operating model · 28% lower run cost",
                    "Security uplift · Critical risks closed in 12 weeks",
                    "Process redesign · 1,400 hours returned annually",
                ),
            )
        }
        item {
            InsightSection(
                title = "Knowledge base",
                entries = listOf(
                    "The 2026 CIO agenda: resilience before novelty",
                    "A practical framework for responsible AI adoption",
                    "Seven signals your cloud model needs a reset",
                ),
            )
        }
    }
}

@Composable
private fun InsightSection(
    title: String,
    entries: List<String>,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        entries.forEach { entry ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = entry,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun NextAvailableBanner() {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(OctanePrimary, Color(0xFF607D8B))))
                .padding(20.dp),
        ) {
            Text("NEXT AVAILABLE", color = OctaneAccent, style = MaterialTheme.typography.labelLarge)
            Text("Tomorrow · 9:30 AM", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Text("Reserve a focused discovery session with a senior consultant.", color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
private fun CategoryRow() {
    val categories = listOf(
        Triple("Security", Icons.Default.Security, Color(0xFFD85B36)),
        Triple("Cloud", Icons.Default.Cloud, Color(0xFF2678B8)),
        Triple("Data & AI", Icons.Default.DataUsage, Color(0xFF6B5CC5)),
        Triple("Operations", Icons.Default.SettingsSuggest, Color(0xFF16835A)),
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(categories) { category ->
            Card(modifier = Modifier.size(104.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Icon(category.second, null, tint = category.third, modifier = Modifier.size(32.dp))
                    Text(category.first, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: ServiceModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(106.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp),
            ) {
                Text(service.category.uppercase(), color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelSmall)
                Text(service.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    service.description,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("From £${service.price.toInt()}", style = MaterialTheme.typography.labelLarge)
                    Surface(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)) {
                        Text(
                            "Book Now",
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}
