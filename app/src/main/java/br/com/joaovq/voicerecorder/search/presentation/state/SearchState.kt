package br.com.joaovq.voicerecorder.search.presentation.state

import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity

sealed class SearchState {
    data object NotFound : SearchState()
    data object Loading : SearchState()
    data class Error(val message: String) : SearchState()
    data class Success(val data: List<AudioRecordEntity>) : SearchState()
}
