package octanecde.digitalization.octanestrategylink.di

import octanecde.digitalization.octanestrategylink.data.repository.BookingRepository
import octanecde.digitalization.octanestrategylink.data.repository.PPTKNOnboardingRepo
import octanecde.digitalization.octanestrategylink.data.repository.ServiceRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        PPTKNOnboardingRepo(
            pptknOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ServiceRepository() }

    single{
        BookingRepository(
            bookingDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}