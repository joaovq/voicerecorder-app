package br.com.joaovq.voicerecorder.home.presentation.components.bottomsheet

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import br.com.joaovq.voicerecorder.R
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAudio(
    modifier: Modifier = Modifier,
    onClickDelete: (AudioRecordEntity) -> Unit = {},
    modalBottomSheetAudioState: BottomSheetAudioState<AudioRecordEntity>,
    onDismissRequest: () -> Unit = {},
    shape: Shape = RoundedCornerShape(10.dp)
) {
    val context = LocalContext.current
    var isVisibleAlertDialog by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(visible = isVisibleAlertDialog) {
        AlertDialog(
            onDismissRequest = { isVisibleAlertDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        modalBottomSheetAudioState.item?.let { onClickDelete(it) }
                        isVisibleAlertDialog = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isVisibleAlertDialog = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.dialog_title_delete_audio))
            }
        )
    }
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        shape = shape
    ) {
        Column(
            modifier = Modifier
        ) {
            BottomSheetMenuItem(
                modifier = Modifier
                    .clickable {
                        modalBottomSheetAudioState.item?.let {
                            sendFile(
                                context,
                                File(it.uriToAudio)
                            )
                        }
                    }
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.share)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(id = R.string.share_icon_content_description)
                )
            }
            BottomSheetMenuItem(
                modifier = Modifier
                    .clickable { isVisibleAlertDialog = true }
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.remove_audio)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = stringResource(id = R.string.remove_audio)
                )
            }
        }
    }
}

private fun sendFile(context: Context, file: File) {
    try {
        val uri = FileProvider.getUriForFile(
            context,
            "br.com.joaovq.voicerecorder.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "audio/*"
        }
        context.startActivity(Intent.createChooser(intent, null))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Stable
data class BottomSheetAudioState<out T>(
    val isVisible: Boolean = false,
    val item: T? = null
)
