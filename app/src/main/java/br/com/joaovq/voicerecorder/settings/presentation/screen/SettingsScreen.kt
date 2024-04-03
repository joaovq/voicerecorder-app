package br.com.joaovq.voicerecorder.settings.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.joaovq.voicerecorder.settings.presentation.vm.SettingsEvent
import br.com.joaovq.voicerecorder.settings.presentation.vm.SettingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onPopBackStack: () -> Unit = {},
    state: SettingsState = SettingsState(),
    onEvent: (SettingsEvent) -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Settings") }, navigationIcon = {
            IconButton(onClick = onPopBackStack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        })
    }) {
        LazyColumn(modifier = modifier.padding(it)) {
            item {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    text = "System settings",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            item {
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                Column {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                val darkTheme = state.isDarkTheme
                                onEvent(SettingsEvent.ChangeDarkTheme(!darkTheme))
                            }
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Dark Theme")
                            Switch(checked = state.isDarkTheme, onCheckedChange = { isChecked ->
                                onEvent(SettingsEvent.ChangeDarkTheme(isChecked))
                            })
                        }
                    }
                }
            }
        }
    }
}