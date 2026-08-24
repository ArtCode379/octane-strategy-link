package octanecde.digitalization.octanestrategylink.ui.composable.screen.servicedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import octanecde.digitalization.octanestrategylink.data.model.ServiceModel
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNContentWrapper
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.viewmodel.ServiceDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val serviceState by viewModel.serviceState.collectAsState()

    LaunchedEffect(serviceId) {
        viewModel.observeServiceById(serviceId)
    }

    PPTKNContentWrapper(
        dataState = serviceState,
        dataPopulated = {
            ServicesDetailsPopulated(
                service = (serviceState as DataUiState.Populated).data,
                modifier = modifier,
                onNavigateToCheckout = onNavigateToCheckout,
            )
        },
        dataEmpty = {
            Column(modifier.fillMaxSize()) {
                Text("Service details are unavailable.", modifier = Modifier.padding(24.dp))
            }
        },
    )
}

@Composable
private fun ServicesDetailsPopulated(
    service: ServiceModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    var selectedSlot by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp),
    ) {
        item {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            )
        }
        item {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                AssistChip(
                    onClick = { selectedSlot = selectedSlot },
                    label = { Text(service.category) },
                )
                Text(service.name, style = MaterialTheme.typography.titleLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("From £${service.price.toInt()}", style = MaterialTheme.typography.titleMedium)
                    Text("· ${service.durationMinutes} min", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(service.description, style = MaterialTheme.typography.bodyLarge)
                Text("What you will receive", style = MaterialTheme.typography.titleMedium)
                service.features.forEach { feature ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.tertiary)
                        Text(feature, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Text("Available consultation times", style = MaterialTheme.typography.titleMedium)
            }
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(service.availableTime.orEmpty()) { time ->
                    val index = service.availableTime.orEmpty().indexOf(time)
                    Surface(
                        onClick = { selectedSlot = index },
                        color = if (selectedSlot == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        contentColor = if (selectedSlot == index) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        shape = RoundedCornerShape(12.dp),
                        tonalElevation = 2.dp,
                    ) {
                        Text(time.toString(), modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp))
                    }
                }
            }
        }
        item {
            Card(modifier = Modifier.padding(20.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Senior consultant included", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Your session includes a concise written summary and clear recommended next steps.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            Button(
                onClick = { onNavigateToCheckout(service.id) },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(54.dp),
            ) {
                Text("Book Consultation")
            }
        }
    }
}

