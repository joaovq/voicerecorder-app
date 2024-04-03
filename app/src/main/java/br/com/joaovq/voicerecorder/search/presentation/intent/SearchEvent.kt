package br.com.joaovq.voicerecorder.search.presentation.intent

import androidx.compose.ui.text.input.TextFieldValue

sealed interface SearchEvent {
    data class Search(val query: TextFieldValue) : SearchEvent
}