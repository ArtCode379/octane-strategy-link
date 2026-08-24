package octanecde.digitalization.octanestrategylink.data.repository

import octanecde.digitalization.octanestrategylink.data.datastore.PPTKNOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PPTKNOnboardingRepo(
    private val pptknOnboardingStoreManager: PPTKNOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return pptknOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            pptknOnboardingStoreManager.setOnboardedState(state)
        }
    }
}