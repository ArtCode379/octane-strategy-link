package octanecde.digitalization.octanestrategylink.di

import octanecde.digitalization.octanestrategylink.ui.viewmodel.BookingViewModel
import octanecde.digitalization.octanestrategylink.ui.viewmodel.CheckoutViewModel
import octanecde.digitalization.octanestrategylink.ui.viewmodel.PPTKNOnboardingVM
import octanecde.digitalization.octanestrategylink.ui.viewmodel.ServiceDetailsViewModel
import octanecde.digitalization.octanestrategylink.ui.viewmodel.ServiceViewModel
import octanecde.digitalization.octanestrategylink.ui.viewmodel.PPTKNSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        PPTKNSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        PPTKNOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ServiceViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        ServiceDetailsViewModel(
            serviceRepository = get()
        )
    }

    viewModel {
        BookingViewModel(
            bookingRepository = get(),
            serviceRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            bookingRepository = get(),
        )
    }
}