package br.com.joaovq.voicerecorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.joaovq.voicerecorder.presentation.VoiceRecorderApp
import br.com.joaovq.voicerecorder.ui.theme.VoiceRecorderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }
        enableEdgeToEdge()
        setContent {
            VoiceRecorderTheme(
                dynamicColor = false,
                content = { VoiceRecorderApp() }
            )
        }
    }
}