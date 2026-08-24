package octanecde.digitalization.octanestrategylink.ui.composable.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import octanecde.digitalization.octanestrategylink.R
import octanecde.digitalization.octanestrategylink.data.model.ServiceModel
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNContentWrapper
import octanecde.digitalization.octanestrategylink.ui.composable.shared.PPTKNEmptyView
import octanecde.digitalization.octanestrategylink.ui.state.DataUiState
import octanecde.digitalization.octanestrategylink.ui.viewmodel.ServiceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val servicesState by viewModel.servicesState.collectAsState()

    HomeContent(
        servicesState = servicesState,
        modifier = modifier,
        onNavigateToServiceDetails = onNavigateToServiceDetails
    )
}

@Composable
private fun HomeContent(
    servicesState: DataUiState<List<ServiceModel>>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        //Place data-independent UI here

        PPTKNContentWrapper(
            dataState = servicesState,

            dataPopulated = {
                // Place the list of products, filters, and other data-dependent content here.
                // If you want to place data-independent elements (e.g. a screen title),
                // put them outside the PPTKNContentWrapper.
                ServicesPopulated(
                    services = (servicesState as DataUiState.Populated).data,
                    onNavigateToServiceDetails = onNavigateToServiceDetails,
                )
            },

            dataEmpty = {
                PPTKNEmptyView(
                    primaryText = stringResource(R.string.pptkn_services_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ServicesPopulated(
    services: List<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {

}