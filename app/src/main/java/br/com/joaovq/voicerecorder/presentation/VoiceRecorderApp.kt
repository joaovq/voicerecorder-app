package br.com.joaovq.voicerecorder.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.joaovq.voicerecorder.home.presentation.screen.HomeScreen
import br.com.joaovq.voicerecorder.home.presentation.vm.HomeViewModel
import br.com.joaovq.voicerecorder.presentation.navgraph.Route
import br.com.joaovq.voicerecorder.recorder.presentation.RecorderScreen
import br.com.joaovq.voicerecorder.recorder.presentation.vm.RecorderViewModel
import br.com.joaovq.voicerecorder.search.presentation.screen.SearchScreen
import br.com.joaovq.voicerecorder.search.presentation.vm.SearchViewModel
import br.com.joaovq.voicerecorder.settings.presentation.screen.SettingsScreen
import br.com.joaovq.voicerecorder.settings.presentation.vm.SettingsViewModel

@Composable
fun VoiceRecorderApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        val navController = rememberNavController()
        val launcherActivityResult = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { _ -> }
        )
        SideEffect {
            launcherActivityResult.launch(Manifest.permission.RECORD_AUDIO)
        }
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(Route.HOME) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val state by homeViewModel.homeState.collectAsState()
                HomeScreen(
                    onNavSettings = { navController.navigate(Route.SETTINGS) },
                    onClickFabButton = { navController.navigate(Route.RECORDER) },
                    onClickSearch = { navController.navigate(Route.SEARCH) },
                    state = state,
                    onEvent = homeViewModel::onEvent
                )
            }
            composable(Route.RECORDER) {
                val viewModel = hiltViewModel<RecorderViewModel>()
                RecorderScreen(
                    event = viewModel::onEvent,
                    recordState = viewModel.recordState.value,
                    onNavigateBack = navController::popBackStack
                )
            }
            composable(Route.SETTINGS) {
                val settingsViewModel = hiltViewModel<SettingsViewModel>()
                val state by settingsViewModel.state.collectAsState()
                SettingsScreen(
                    onPopBackStack = navController::popBackStack,
                    onEvent = settingsViewModel::onEvent,
                    state = state
                )
            }
            composable(Route.SEARCH) {
                val viewModel = hiltViewModel<SearchViewModel>()
                val state by viewModel.state.collectAsState()
                SearchScreen(
                    query = viewModel.query,
                    searchState = state,
                    onEvent = viewModel::onEvent,
                    onClickNavIcon = navController::popBackStack
                )
            }
        }
    }
}