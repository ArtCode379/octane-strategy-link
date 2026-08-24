package octanecde.digitalization.octanestrategylink.ui.composable.screen.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import octanecde.digitalization.octanestrategylink.data.entity.BookingEntity
import octanecde.digitalization.octanestrategylink.data.repository.ServiceRepository
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToBookingsScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val bookingState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    var phone by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val service = remember(serviceId) { ServiceRepository().getById(serviceId) }
    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank() &&
                phone.isNotBlank() &&
                selectedDate.isNotBlank()
        }
    }

    if (bookingState is DataUiState.Populated) {
        CheckoutDialog(
            booking = (bookingState as DataUiState.Populated<BookingEntity>).data,
            onConfirm = onNavigateToBookingsScreen,
        )
    }

    CheckoutContent(
        serviceName = service?.name.orEmpty(),
        servicePrice = service?.price ?: 0.0,
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        phone = phone,
        selectedDate = selectedDate,
        notes = notes,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = focusManager,
        isButtonEnabled = isButtonEnabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onNotesChanged = { notes = it },
        onDateClick = { showDatePicker = true },
        onPlaceBookingButtonClick = { viewModel.placeBooking(serviceId) },
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { milliseconds ->
                            selectedDate = Instant.ofEpochMilli(milliseconds)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                                .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                        }
                        showDatePicker = false
                    },
                ) {
                    Text("Select")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun CheckoutContent(
    serviceName: String,
    servicePrice: Double,
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    phone: String,
    selectedDate: String,
    notes: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onDateClick: () -> Unit,
    onPlaceBookingButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Book your consultation", style = MaterialTheme.typography.headlineMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(serviceName, style = MaterialTheme.typography.titleMedium)
                Text("From £${servicePrice.toInt()}", color = MaterialTheme.colorScheme.secondary)
            }
        }
        Text("Contact details", style = MaterialTheme.typography.titleMedium)
        CheckoutTextField(customerFirstName, onFirstNameChanged, "First name", Modifier.fillMaxWidth())
        CheckoutTextField(customerLastName, onLastNameChanged, "Last name", Modifier.fillMaxWidth())
        CheckoutTextField(
            customerEmail,
            onEmailChanged,
            "Email",
            Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        CheckoutTextField(
            phone,
            onPhoneChanged,
            "Phone",
            Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { selectedDate.length },
            readOnly = true,
            label = { Text("Preferred date") },
            trailingIcon = { Icon(Icons.Default.CalendarMonth, null) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onDateClick),
        )
        CheckoutTextField(notes, onNotesChanged, "Notes (optional)", Modifier.fillMaxWidth())
        Text(
            "A consultant will confirm the online conference details by email.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
        )
        Button(
            onClick = {
                focusManager.clearFocus()
                onPlaceBookingButtonClick()
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
        ) {
            Text("Confirm Booking")
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText, style = MaterialTheme.typography.titleSmall) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}

