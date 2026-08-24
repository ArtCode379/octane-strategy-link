package octanecde.digitalization.octanestrategylink.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNContentWrapper
import octanecde.digitalization.octanestrategylink.ui.state.BookingUiState
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.theme.OctaneChipBackground
import octanecde.digitalization.octanestrategylink.ui.theme.OctaneChipContent
import octanecde.digitalization.octanestrategylink.ui.viewmodel.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = koinViewModel(),
) {
    val bookingsState by viewModel.bookingsState.collectAsState()
    var bookingToCancel by remember { mutableStateOf<String?>(null) }

    BookingsContent(
        bookingsState = bookingsState,
        modifier = modifier,
        onCancelBookingButtonClick = { bookingToCancel = it },
    )

    bookingToCancel?.let { bookingNumber ->
        AlertDialog(
            onDismissRequest = { bookingToCancel = null },
            title = { Text("Cancel this booking?") },
            text = { Text("The reserved consultation time will be released.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelBooking(bookingNumber)
                        bookingToCancel = null
                    },
                ) {
                    Text("Cancel booking", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { bookingToCancel = null }) {
                    Text("Keep booking")
                }
            },
        )
    }
}

@Composable
private fun BookingsContent(
    bookingsState: DataUiState<List<BookingUiState>>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (String) -> Unit,
) {
    PPTKNContentWrapper(
        dataState = bookingsState,
        dataPopulated = {
            BookingsPopulated(
                bookings = (bookingsState as DataUiState.Populated).data,
                modifier = modifier,
                onCancelBookingButtonClick = onCancelBookingButtonClick,
            )
        },
        dataEmpty = {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No bookings yet", style = MaterialTheme.typography.titleLarge)
                    Text("Browse services from Home to schedule your first consultation.")
                }
            }
        },
    )
}

@Composable
private fun BookingsPopulated(
    bookings: List<BookingUiState>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Your consultations", style = MaterialTheme.typography.headlineMedium)
        }
        items(bookings, key = { it.bookingNumber }) { booking ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(booking.serviceName, style = MaterialTheme.typography.titleMedium)
                        Surface(
                            color = OctaneChipBackground,
                            contentColor = OctaneChipContent,
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Text("Confirmed", modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                    Text(booking.timestamp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Session #${booking.bookingNumber}", style = MaterialTheme.typography.labelLarge)
                    TextButton(onClick = { onCancelBookingButtonClick(booking.bookingNumber) }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

