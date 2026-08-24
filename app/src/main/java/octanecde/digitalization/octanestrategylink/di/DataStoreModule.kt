package octanecde.digitalization.octanestrategylink.di

import octanecde.digitalization.octanestrategylink.data.datastore.PPTKNOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { PPTKNOnboardingPrefs(androidContext()) }
}