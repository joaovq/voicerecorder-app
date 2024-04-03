package br.com.joaovq.voicerecorder.recorder.data.state

sealed interface MediaRecordState {
    data object Recording: MediaRecordState
    data object Idle: MediaRecordState
}