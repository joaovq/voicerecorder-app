package br.com.joaovq.voicerecorder.recorder.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.joaovq.voicerecorder.BuildConfig
import br.com.joaovq.voicerecorder.R
import br.com.joaovq.voicerecorder.presentation.components.AdMobBanner
import br.com.joaovq.voicerecorder.recorder.presentation.components.ControlButtons
import br.com.joaovq.voicerecorder.recorder.presentation.intent.RecordEvent
import br.com.joaovq.voicerecorder.recorder.presentation.state.RecordState
import br.com.joaovq.voicerecorder.ui.theme.Oswald
import br.com.joaovq.voicerecorder.ui.theme.VoiceRecorderTheme
import kotlinx.coroutines.launch
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecorderScreen(
    modifier: Modifier = Modifier,
    event: (RecordEvent) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    recordState: RecordState = RecordState(false)
) {
    val bottomSheet = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    AnimatedVisibility(visible = bottomSheet.isVisible) {
        ModalBottomSheet(
            sheetState = bottomSheet,
            onDismissRequest = {},
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = recordState.fileName,
                    onValueChange = {
                        event(RecordEvent.ChangeFileName(it))
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.placeholder_save_file))
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onNavigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            event(RecordEvent.SaveRecord)
                            onNavigateBack()
                        },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White)
                    ) {
                        Text(text = stringResource(R.string.text_button_save_record))
                    }
                }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        AdMobBanner(
            modifier = Modifier.align(Alignment.TopCenter),
            adUnitId = BuildConfig.BANNER_ID
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = .9f),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            val context = LocalContext.current
            AnimatedContent(
                targetState = recordState.time.let {
                    val seconds = it % 60
                    val minutes = (it / 60) % 60
                    val hours = (it / (60 * 60)) % 60
                    "$hours:$minutes:$seconds"
                },
                label = "timer",
            ) { targetState ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = targetState,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Oswald,
                        fontSize = 80.sp
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            ControlButtons(
                onClickPause = { event(RecordEvent.Pause) },
                onClickStart = {
                    if (recordState.isRecording) {
                        event(RecordEvent.Stop)
                        scope.launch { bottomSheet.show() }
                    } else if (recordState.time != 0L) {
                        event(RecordEvent.Resume)
                    } else event(
                        RecordEvent.Start(
                            File(
                                context.cacheDir,
                                "audio_${System.currentTimeMillis()}.mp3"
                            )
                        )
                    )
                },
                onClickCheck = {
                },
                isRecording = recordState.isRecording
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecorderPreview() {
    VoiceRecorderTheme {
        RecorderScreen()
    }
}