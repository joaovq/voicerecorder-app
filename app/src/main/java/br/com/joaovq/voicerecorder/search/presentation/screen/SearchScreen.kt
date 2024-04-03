package br.com.joaovq.voicerecorder.search.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import br.com.joaovq.voicerecorder.R
import br.com.joaovq.voicerecorder.home.presentation.components.files.FilesList
import br.com.joaovq.voicerecorder.presentation.components.SearchTextField
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import br.com.joaovq.voicerecorder.search.presentation.intent.SearchEvent
import br.com.joaovq.voicerecorder.search.presentation.state.SearchState
import br.com.joaovq.voicerecorder.ui.theme.VoiceRecorderTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: TextFieldValue = TextFieldValue(),
    searchState: SearchState = SearchState.NotFound,
    onEvent: (SearchEvent) -> Unit = {},
    onClickNavIcon: () -> Unit = {}
) {
    val focusRequester = remember {
        FocusRequester()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClickNavIcon) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                SearchTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .testTag("search-field"),
                    value = query,
                    onValueChange = { textFieldValue ->
                        onEvent(SearchEvent.Search(textFieldValue))
                    }
                )
            }
        }
    ) {
        when (searchState) {
            is SearchState.Error -> {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    Text(text = stringResource(R.string.records_not_found))
                }
            }

            SearchState.NotFound -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding() + 8.dp, start = 10.dp),
                ) {
                    Text(text = stringResource(R.string.records_not_found))
                }
            }

            is SearchState.Success -> {
                FilesList(
                    modifier = Modifier.padding(it),
                    lazyListState = rememberLazyListState(),
                    files = searchState.data,
                    onClickItemList = { file ->
                        TODO("Not yet implemented $file")
                    },
                    showMenu = false
                )
            }

            SearchState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

class StateParameterProvider : PreviewParameterProvider<SearchState> {
    override val values: Sequence<SearchState>
        get() = sequenceOf(
            SearchState.NotFound,
            SearchState.Loading,
            SearchState.Success(
                listOf(
                    AudioRecordEntity(
                        name = "Meeting 1",
                        uriToAudio = ""
                    )
                )
            )
        )

}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(
    @PreviewParameter(StateParameterProvider::class) state: SearchState
) {
    VoiceRecorderTheme {
        SearchScreen(
            searchState = state
        )
    }
}