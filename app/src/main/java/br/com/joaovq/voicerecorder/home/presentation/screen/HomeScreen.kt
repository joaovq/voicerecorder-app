package br.com.joaovq.voicerecorder.home.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import br.com.joaovq.voicerecorder.R
import br.com.joaovq.voicerecorder.home.presentation.components.bottomsheet.BottomSheetAudio
import br.com.joaovq.voicerecorder.home.presentation.components.bottomsheet.BottomSheetAudioState
import br.com.joaovq.voicerecorder.home.presentation.components.bottomsheet.rememberBottomSheetAudioState
import br.com.joaovq.voicerecorder.home.presentation.components.files.FilesList
import br.com.joaovq.voicerecorder.home.presentation.intent.HomeEvent
import br.com.joaovq.voicerecorder.home.presentation.screen.notfound.FilesNotFound
import br.com.joaovq.voicerecorder.home.presentation.state.HomeState
import br.com.joaovq.voicerecorder.presentation.components.SearchTextField
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavSettings: () -> Unit = {},
    onClickFabButton: () -> Unit = {},
    onClickSearch: () -> Unit = {},
    onEvent: (HomeEvent) -> Unit = {},
    state: HomeState = HomeState()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    val files = context.cacheDir.listFiles()
    var bottomSheetAudioState by rememberBottomSheetAudioState<AudioRecordEntity>()
    AnimatedVisibility(visible = bottomSheetAudioState.isVisible) {
        BottomSheetAudio(
            modalBottomSheetAudioState = bottomSheetAudioState,
            onDismissRequest = {
                bottomSheetAudioState = bottomSheetAudioState.copy(isVisible = false)
            },
            onClickDelete = { audio ->
                onEvent(HomeEvent.DeleteRecord(audio))
            }
        )
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.home_title_tab_bar))
                    },
                    actions = {
                        IconButton(onClick = onNavSettings) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(
                                    id = R.string.home_settings_icon_content_description
                                )
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
                SearchTextField(
                    readOnly = false,
                    onPressed = onClickSearch
                )
            }
        },
        floatingActionButton = {
            val isShowedFirstIndex by remember {
                derivedStateOf { lazyListState.firstVisibleItemIndex == 0 }
            }
            AnimatedVisibility(
                visible = isShowedFirstIndex,
                enter = slideInVertically(
                    initialOffsetY = {
                        it * 2
                    }
                ),
                exit = slideOutVertically {
                    it * 2
                }
            ) {
                FloatingActionButton(
                    modifier = Modifier.imePadding(),
                    onClick = onClickFabButton,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardVoice,
                        contentDescription = stringResource(id = R.string.home_fab_content_description),
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.files.isEmpty() -> {
                FilesNotFound(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )
            }

            else -> {
                FilesList(
                    modifier = Modifier
                        .padding(it)
                        .imePadding()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    lazyListState = lazyListState,
                    files = state.files,
                    onClickItemList = { file -> onEvent(HomeEvent.PlayAudio(file)) },
                    onClickMenu = { selectedAudio ->
                        bottomSheetAudioState =
                            bottomSheetAudioState.copy(isVisible = true, item = selectedAudio)
                    }
                )
            }
        }
    }
}