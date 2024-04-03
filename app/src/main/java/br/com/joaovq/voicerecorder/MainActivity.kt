package br.com.joaovq.voicerecorder

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.joaovq.voicerecorder.presentation.VoiceRecorderApp
import br.com.joaovq.voicerecorder.presentation.vm.MainState
import br.com.joaovq.voicerecorder.presentation.vm.MainViewModel
import br.com.joaovq.voicerecorder.ui.theme.VoiceRecorderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        var state by mutableStateOf(MainState())
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.state.onEach {
                    state = it
                }.collect()
            }
        }
        splashScreen.apply {
            setKeepOnScreenCondition { state.isLoading }
        }
        setContent {
            val darkTheme = when {
                state.isLoading -> isSystemInDarkTheme()
                else -> state.darkTheme
            }
            setStatusBarColor(color = Color.TRANSPARENT, darkTheme = state.darkTheme)
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme }
                )
                onDispose {}
            }
            VoiceRecorderTheme(
                dynamicColor = false,
                content = { VoiceRecorderApp() },
                darkTheme = state.darkTheme
            )
        }
    }
}

@Composable
fun setStatusBarColor(color: Int, darkTheme: Boolean) {
    val view = LocalView.current
    val colorPrimary = colorScheme.primary.toArgb()
    if (!view.isInEditMode) {
        LaunchedEffect(key1 = null) {
            val window = (view.context as Activity).window
            window.statusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                color
            } else {
                colorPrimary
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
}