package br.com.joaovq.voicerecorder.recorder.data.database.datasource

sealed class AudioRecordDataState {
    data class Success<T>(val data: T) : AudioRecordDataState()
    data class Error(val exception: Exception, val message: String) : AudioRecordDataState()
}