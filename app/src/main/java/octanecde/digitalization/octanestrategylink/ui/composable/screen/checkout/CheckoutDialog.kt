package octanecde.digitalization.octanestrategylink.ui.composable.screen.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import octanecde.digitalization.octanestrategylink.data.entity.BookingEntity

@Composable
fun CheckoutDialog(
    booking: BookingEntity,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Consultation confirmed") },
        text = {
            Text(
                "Session #${booking.bookingNumber} is reserved. Your consultant will be waiting in the online conference at the appointed time. Connection details will be shared by email.",
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("View my bookings")
            }
        },
    )
}

