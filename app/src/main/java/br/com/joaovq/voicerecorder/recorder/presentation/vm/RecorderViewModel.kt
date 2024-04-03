package br.com.joaovq.voicerecorder.recorder.presentation.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.joaovq.voicerecorder.data.service.AudioRecorder
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDataState
import br.com.joaovq.voicerecorder.recorder.data.database.datasource.AudioRecordDatasource
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import br.com.joaovq.voicerecorder.recorder.presentation.intent.RecordEvent
import br.com.joaovq.voicerecorder.recorder.presentation.state.RecordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.timer

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val audioRecorderDatasource: AudioRecordDatasource<AudioRecordEntity, AudioRecordDataState>,
    private val audioRecorder: AudioRecorder
) : ViewModel() {
    var recordState: MutableState<RecordState> = mutableStateOf(RecordState(false))
        private set
    private var timer: Timer? = null

    private val log = Timber.tag(this::class.java.simpleName)

    fun onEvent(event: RecordEvent) {
        when (event) {
            RecordEvent.Pause -> pauseRecord()
            is RecordEvent.Start -> startRecord(event.outputFile)
            RecordEvent.Stop -> stopRecord()
            RecordEvent.Cancel -> stopRecord()
            RecordEvent.Resume -> {
                audioRecorder.resume()
            }

            is RecordEvent.SaveRecord -> {
                recordState.value.fileName.text.let { fileName ->
                    if (fileName.isNotBlank()) saveRecord(fileName)
                }
            }

            is RecordEvent.ChangeFileName -> {
                recordState.value = recordState.value.copy(fileName = event.value)
            }
        }
    }

    private fun saveRecord(fileName: String) {
        viewModelScope.launch {
            log.d("Save record")
            audioRecorderDatasource.saveRecord(
                AudioRecordEntity(
                    name = fileName,
                    uriToAudio = recordState.value.file?.absolutePath.toString()
                )
            )
        }
    }

    private fun startRecord(outputFile: File) {
        audioRecorder.start(outputFile)
        recordState.value = recordState.value.copy(isRecording = true, file = outputFile)
        timer = timer(initialDelay = 1000, period = 1000) {
            recordState.value = recordState.value.copy(time = recordState.value.time + 1)
        }
    }

    private fun pauseRecord() {
        audioRecorder.pause()
        recordState.value = recordState.value.copy(isRecording = false)
    }

    private fun stopRecord() {
        audioRecorder.stop()
        recordState.value = recordState.value.copy(isRecording = false, time = 0L)
        timer?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        audioRecorder.release()
        timer = null
    }
}