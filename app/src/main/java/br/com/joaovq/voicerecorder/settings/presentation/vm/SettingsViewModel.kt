package br.com.joaovq.voicerecorder.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.voicerecorder.data.storage.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _isDarkTheme = userRepository.getDarkThemePreference()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )
    private val _state = MutableStateFlow(SettingsState())
    val state = combine(_state, _isDarkTheme) { state, isDarkTheme ->
        state.copy(isDarkTheme = isDarkTheme)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsState())

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeDarkTheme -> {
                viewModelScope.launch {
                    userRepository.setDarkThemePreference(event.value)
                }
            }
        }
    }
}

data class SettingsState(
    val isDarkTheme: Boolean = false
)

sealed class SettingsEvent {
    data class ChangeDarkTheme(val value: Boolean) : SettingsEvent()
}