package br.com.joaovq.voicerecorder.home.presentation.intent

import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import java.io.File

sealed interface HomeEvent {
    data class PlayAudio(val file: File) : HomeEvent
    data class DeleteRecord(val audioRecordEntity: AudioRecordEntity) : HomeEvent
}