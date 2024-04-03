package br.com.joaovq.voicerecorder.home.presentation.components.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import java.io.File

@Composable
fun <T> rememberBottomSheetAudioState(): MutableState<BottomSheetAudioState<T>> {
    val state = remember {
        mutableStateOf(BottomSheetAudioState<T>())
    }
    return state
}