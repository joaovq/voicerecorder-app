package br.com.joaovq.voicerecorder.home.presentation.components.files

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.joaovq.voicerecorder.recorder.data.database.entity.AudioRecordEntity
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesList(
    modifier: Modifier = Modifier,
    files: List<AudioRecordEntity> = listOf(),
    lazyListState: LazyListState,
    onClickItemList: (file: File) -> Unit = {},
    onClickMenu: (audioRecord: AudioRecordEntity) -> Unit = {},
    showMenu: Boolean = true
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        items(files, key = { file -> file.id }) { audioRecord ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.elevatedCardColors(),
                onClick = { onClickItemList(File(audioRecord.uriToAudio)) }
            ) {
                Row(modifier = Modifier.padding(vertical = 20.dp, horizontal = 15.dp)) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            modifier = Modifier.border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            ),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                        )
                        Column(Modifier.weight(1f)) {
                            Text(text = audioRecord.name)
                            Text(
                                text = audioRecord.name,
                                style = MaterialTheme.typography.labelSmall.copy(color = Color.LightGray)
                            )
                        }
                    }
                    if (showMenu) {
                        IconButton(
                            onClick = { onClickMenu(audioRecord) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu vert"
                            )
                        }
                    }
                }
            }
        }
    }
}