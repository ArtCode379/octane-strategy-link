package octanecde.digitalization.octanestrategylink.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import octanecde.digitalization.octanestrategylink.data.repository.PPTKNOnboardingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PPTKNOnboardingVM(
    private val onboardingRepository: PPTKNOnboardingRepo,
) : ViewModel() {
    private val _onboardingSetState = MutableStateFlow(false)
    val onboardingSetState: StateFlow<Boolean>
        get() = _onboardingSetState.asStateFlow()

    fun setOnboarded() {
        viewModelScope.launch {
            onboardingRepository.setOnboardingState(true)
            _onboardingSetState.update { true }
        }
    }
}