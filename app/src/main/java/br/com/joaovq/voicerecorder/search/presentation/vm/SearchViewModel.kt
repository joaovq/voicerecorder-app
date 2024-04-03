package br.com.joaovq.voicerecorder.search.presentation.vm

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDataState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatasource
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import br.com.joaovq.voicerecorder.search.presentation.intent.SearchEvent
import br.com.joaovq.voicerecorder.search.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val audioRecordDatasource: AudioRecordDatasource<AudioRecordEntity, AudioRecordDataState>,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    @OptIn(SavedStateHandleSaveableApi::class)
    var query by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
        private set
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.NotFound)
    val state: StateFlow<SearchState> = _state.asStateFlow()
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value = SearchState.Loading
                    query = event.query
                    delay(2000)
                    val records = audioRecordDatasource.searchRecordByName(event.query.text)
                    records.collectLatest {
                        _state.value = when {
                            it.isNotEmpty() -> {
                                SearchState.Success(it)
                            }

                            else -> SearchState.NotFound
                        }
                    }
                }
            }
        }
    }
}