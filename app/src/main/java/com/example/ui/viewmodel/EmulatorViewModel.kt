package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EmulatorViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).appDao()

    private val _currentTab = MutableStateFlow("home")
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    val allGames: StateFlow<List<GameEntity>> = dao.getAllGames()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeGame = MutableStateFlow<GameEntity?>(null)
    val activeGame: StateFlow<GameEntity?> = _activeGame.asStateFlow()

    val settings: StateFlow<AppSettingsEntity> = dao.getSettings()
        .map { it ?: AppSettingsEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettingsEntity())

    val memoryCards: StateFlow<List<MemoryCardEntity>> = dao.getMemoryCards()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _scanMessage = MutableStateFlow<String?>(null)
    val scanMessage: StateFlow<String?> = _scanMessage.asStateFlow()

    init {
        viewModelScope.launch {
            dao.insertSettings(AppSettingsEntity())
            val cards = dao.getMemoryCards().first()
            if (cards.isEmpty()) {
                dao.insertMemoryCards(
                    listOf(
                        MemoryCardEntity(1, "Memory Card Slot 1 (PS2)", 1024L * 1536L, 1024L * 1024L * 8L),
                        MemoryCardEntity(2, "Memory Card Slot 2 (PS2)", 1024L * 512L, 1024L * 1024L * 8L)
                    )
                )
            }
        }
    }

    fun setCurrentTab(tab: String) {
        _currentTab.value = tab
    }

    fun startGame(game: GameEntity) {
        _activeGame.value = game
        _currentTab.value = "play"
        viewModelScope.launch {
            dao.updateGame(game.copy(lastPlayedTimestamp = System.currentTimeMillis(), playTimeMinutes = game.playTimeMinutes + 5))
        }
    }

    fun stopGame() {
        _activeGame.value = null
        _currentTab.value = "home"
    }

    fun updateSettings(newSettings: AppSettingsEntity) {
        viewModelScope.launch {
            dao.insertSettings(newSettings)
        }
    }

    fun scanGames() {
        viewModelScope.launch {
            _isScanning.value = true
            _scanMessage.value = "Memindai direktori perangkat untuk file ISO/BIN PS2..."
            kotlinx.coroutines.delay(2000)
            
            val newGame = GameEntity(
                title = "Final Fantasy X (PS2 Remaster)",
                filePath = "/storage/emulated/0/Download/Final_Fantasy_X.iso",
                fileSize = "4.5 GB",
                isFavorite = false,
                playTimeMinutes = 15,
                coverUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=500"
            )
            dao.insertGame(newGame)
            _isScanning.value = false
            _scanMessage.value = "Berhasil menemukan 1 game baru!"
        }
    }

    fun toggleFavorite(game: GameEntity) {
        viewModelScope.launch {
            dao.updateGame(game.copy(isFavorite = !game.isFavorite))
        }
    }

    fun deleteGame(id: Long) {
        viewModelScope.launch {
            dao.deleteGame(id)
        }
    }

    fun getSaveStates(gameId: Long): Flow<List<SaveStateEntity>> {
        return dao.getSaveStates(gameId)
    }

    fun createSaveState(gameId: Long, gameTitle: String, slotNumber: Int, title: String) {
        viewModelScope.launch {
            dao.insertSaveState(
                SaveStateEntity(
                    gameId = gameId,
                    gameTitle = gameTitle,
                    slotNumber = slotNumber,
                    title = title,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteSaveState(id: Long) {
        viewModelScope.launch {
            dao.deleteSaveState(id)
        }
    }

    fun formatMemoryCard(slotNumber: Int) {
        viewModelScope.launch {
            dao.insertMemoryCards(
                listOf(
                    MemoryCardEntity(slotNumber = slotNumber, title = "Memory Card Slot $slotNumber (PS2)", usedBytes = 0L, totalBytes = 1024L * 1024L * 8L)
                )
            )
        }
    }
}
