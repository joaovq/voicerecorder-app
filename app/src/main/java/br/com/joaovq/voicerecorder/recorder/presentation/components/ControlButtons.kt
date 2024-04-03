package br.com.joaovq.voicerecorder.recorder.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ControlButtons(
    modifier: Modifier = Modifier,
    onClickPause: () -> Unit = {},
    onClickStart: () -> Unit = {},
    onClickCheck: () -> Unit = {},
    isRecording: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            onClick = onClickPause
        ) {
            Icon(
                imageVector = Icons.Default.PauseCircleOutline,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        val infiniteTransition = rememberInfiniteTransition(label = "Scale infiniteTransition")
        val scaleFloat by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (isRecording) 1.2f else 1f,
            label = "scale value",
            animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
        )
        IconButton(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scaleFloat
                    scaleY = scaleFloat
                }
                .size(80.dp)
                .clip(CircleShape)
                .composed {
                    if (isRecording)
                        return@composed border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    this
                }
                .padding(10.dp),
            onClick = onClickStart
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(if (isRecording) MaterialTheme.colorScheme.primary else Color.DarkGray),
                tint = Color.White
            )
        }
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            onClick = onClickCheck
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}