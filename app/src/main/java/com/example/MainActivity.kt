package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.EmulatorViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: EmulatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val currentTab by viewModel.currentTab.collectAsState()
                val games by viewModel.allGames.collectAsState()
                val activeGame by viewModel.activeGame.collectAsState()
                val settings by viewModel.settings.collectAsState()
                val memoryCards by viewModel.memoryCards.collectAsState()
                val isScanning by viewModel.isScanning.collectAsState()
                val scanMessage by viewModel.scanMessage.collectAsState()

                if (currentTab == "play" && activeGame != null) {
                    EmulatorPlayScreen(
                        game = activeGame!!,
                        settings = settings,
                        onStopGame = { viewModel.stopGame() },
                        onSaveStateQuick = {
                            activeGame?.let { game ->
                                viewModel.createSaveState(game.id, game.title, 1, "Quick Save State")
                            }
                        }
                    )
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            BottomNavBar(
                                currentTab = currentTab,
                                onTabSelected = { tab -> viewModel.setCurrentTab(tab) }
                            )
                        }
                    ) { innerPadding ->
                        val contentModifier = Modifier.padding(innerPadding)
                        when (currentTab) {
                            "home" -> HomeScreen(
                                games = games,
                                settings = settings,
                                onNavigate = { tab -> viewModel.setCurrentTab(tab) },
                                onStartGame = { game -> viewModel.startGame(game) },
                                onScanGames = { viewModel.scanGames() },
                                isScanning = isScanning,
                                scanMessage = scanMessage ?: ""
                            )
                            "games" -> GameListScreen(
                                games = games,
                                onStartGame = { game -> viewModel.startGame(game) },
                                onScanGames = { viewModel.scanGames() },
                                onToggleFavorite = { game -> viewModel.toggleFavorite(game) },
                                onDeleteGame = { id -> viewModel.deleteGame(id) },
                                isScanning = isScanning,
                                scanMessage = scanMessage ?: ""
                            )
                            "graphics" -> GraphicsSettingsScreen(
                                settings = settings,
                                onUpdateSettings = { newSet -> viewModel.updateSettings(newSet) },
                                onNavigateTab = { tab -> viewModel.setCurrentTab(tab) }
                            )
                            "controls" -> ControlSettingsScreen(
                                settings = settings,
                                onUpdateSettings = { newSet -> viewModel.updateSettings(newSet) },
                                onNavigateTab = { tab -> viewModel.setCurrentTab(tab) }
                            )
                            "audio" -> AudioSettingsScreen(
                                settings = settings,
                                onUpdateSettings = { newSet -> viewModel.updateSettings(newSet) },
                                onNavigateTab = { tab -> viewModel.setCurrentTab(tab) }
                            )
                            "memory" -> MemoryCardScreen(
                                memoryCards = memoryCards,
                                onFormatCard = { slot -> viewModel.formatMemoryCard(slot) },
                                onNavigateTab = { tab -> viewModel.setCurrentTab(tab) }
                            )
                            "savestate" -> SaveStateScreen(
                                games = games,
                                getSaveStates = { gameId -> viewModel.getSaveStates(gameId) },
                                onCreateSaveState = { gameId, gameTitle, slot, title ->
                                    viewModel.createSaveState(gameId, gameTitle, slot, title)
                                },
                                onDeleteSaveState = { id -> viewModel.deleteSaveState(id) },
                                onNavigateTab = { tab -> viewModel.setCurrentTab(tab) }
                            )
                            "device" -> DeviceInfoScreen(
                                onNavigateHome = { viewModel.setCurrentTab("home") }
                            )
                            else -> HomeScreen(
                                games = games,
                                settings = settings,
                                onNavigate = { tab -> viewModel.setCurrentTab(tab) },
                                onStartGame = { game -> viewModel.startGame(game) },
                                onScanGames = { viewModel.scanGames() },
                                isScanning = isScanning,
                                scanMessage = scanMessage ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}
