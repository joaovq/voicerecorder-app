package br.com.joaovq.voicerecorder.recorder.presentation.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import java.io.File

@Stable
data class RecordState(
    val isRecording: Boolean,
    val time: Long = 0,
    val file: File? = null,
    val fileName: TextFieldValue = TextFieldValue()
)
