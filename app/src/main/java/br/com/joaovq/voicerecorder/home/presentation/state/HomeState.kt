package br.com.joaovq.voicerecorder.home.presentation.state

import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity

data class HomeState(
    val isLoading: Boolean = false,
    val files: List<AudioRecordEntity> = listOf()
)