package br.com.joaovq.voicerecorder.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import br.com.joaovq.voicerecorder.R

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholderText: String = stringResource(id = R.string.search),
    onPressed: () -> Unit = {},
    value: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val interactionSource by remember {
        mutableStateOf(MutableInteractionSource())
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    LaunchedEffect(key1 = isPressed) {
        if (isPressed) onPressed()
    }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholderText)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.DarkGray.copy(alpha = .2f),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp),
        readOnly = readOnly,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}