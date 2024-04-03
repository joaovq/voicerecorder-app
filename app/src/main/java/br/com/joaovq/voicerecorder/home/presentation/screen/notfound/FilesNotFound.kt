package br.com.joaovq.voicerecorder.home.presentation.screen.notfound

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.joaovq.voicerecorder.R

@Composable
fun FilesNotFound(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Default.Audiotrack,
                contentDescription = stringResource(R.string.audio_track_icon_content_description)
            )
            Text(text = stringResource(R.string.no_audios_found))
        }
    }
}