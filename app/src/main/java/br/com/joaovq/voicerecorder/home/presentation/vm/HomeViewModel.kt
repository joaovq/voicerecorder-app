package br.com.joaovq.voicerecorder.home.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.voicerecorder.data.service.AudioPlayer
import br.com.joaovq.voicerecorder.home.presentation.intent.HomeEvent
import br.com.joaovq.voicerecorder.home.presentation.state.HomeState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDataState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatasource
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioRecordDatasource: AudioRecordDatasource<AudioRecordEntity, AudioRecordDataState>,
    private val audioPlayer: AudioPlayer
) : ViewModel() {

    private var _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = audioRecordDatasource.getRecords()
        .map {
            _homeState.value = _homeState.value.copy(isLoading = true)
            _homeState.value =
                _homeState.value.copy(files = it)
            _homeState.value = _homeState.value.copy(isLoading = false)
            _homeState.value
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _homeState.value)

    private val log = Timber.tag(this::class.java.simpleName)
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.PlayAudio -> {
                log.d("Play audio")
                playAudio(event.file)
            }

            is HomeEvent.DeleteRecord -> {
                viewModelScope.launch {
                    audioRecordDatasource.deleteRecord(event.audioRecordEntity)
                    val file = File(event.audioRecordEntity.uriToAudio)
                    file.delete()
                }
            }
        }
    }

    private fun playAudio(file: File) {
        audioPlayer.play(file)
    }

    fun stopAudio() {
        audioPlayer.stop()
    }
}