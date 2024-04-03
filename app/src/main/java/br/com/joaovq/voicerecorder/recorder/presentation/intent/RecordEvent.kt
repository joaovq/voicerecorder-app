package br.com.joaovq.voicerecorder.recorder.presentation.intent

import androidx.compose.ui.text.input.TextFieldValue
import java.io.File

sealed interface RecordEvent {
    data class ChangeFileName(val value: TextFieldValue) : RecordEvent
    data object Pause : RecordEvent
    data object Resume : RecordEvent
    data class Start(val outputFile: File) : RecordEvent
    data object Cancel : RecordEvent
    data object SaveRecord : RecordEvent
    data object Stop : RecordEvent
}