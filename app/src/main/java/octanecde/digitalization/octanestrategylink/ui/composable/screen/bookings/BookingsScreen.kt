package octanecde.digitalization.octanestrategylink.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import octanecde.digitalization.octanestrategylink.R
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNContentWrapper
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNEmptyView
import octanecde.digitalization.octanestrategylink.ui.state.BookingUiState
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.viewmodel.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = koinViewModel()
) {
    val bookingsState by viewModel.bookingsState.collectAsState()

    var canceledBookingNumber by remember { mutableStateOf("") }
    var shouldShowDialog by remember { mutableStateOf(false) }

    BookingsContent(
        bookingsState = bookingsState,
        modifier = modifier,
        onCancelBookingButtonClick = { bookingNumber ->
            canceledBookingNumber = bookingNumber
            shouldShowDialog = true
        }
    )

    if (shouldShowDialog) {

    }
}

@Composable
private fun BookingsContent(
    bookingsState: DataUiState<List<BookingUiState>>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (bookingNumber: String) -> Unit,
) {
    Column(modifier = modifier) {

        PPTKNContentWrapper(
            dataState = bookingsState,

            dataPopulated = {
                BookingsPopulated(
                    bookings = (bookingsState as DataUiState.Populated).data,
                    onCancelBookingButtonClick = onCancelBookingButtonClick,
                )
            },

            dataEmpty = {
                PPTKNEmptyView(
                    primaryText = stringResource(R.string.pptkn_bookings_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun BookingsPopulated(
    bookings: List<BookingUiState>,
    modifier: Modifier = Modifier,
    onCancelBookingButtonClick: (bookingNumber: String) -> Unit,
) {

}