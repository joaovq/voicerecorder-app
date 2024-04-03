package br.com.joaovq.voicerecorder.presentation.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.voicerecorder.data.storage.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    private val isDarkTheme = userRepository.getDarkThemePreference()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val state = combine(_state, isDarkTheme) { state, darkTheme ->
        state.copy(
            darkTheme = darkTheme,
            isLoading = false
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MainState(isLoading = true)
    )
}


data class MainState(
    val darkTheme: Boolean = false,
    val isLoading: Boolean = false
)