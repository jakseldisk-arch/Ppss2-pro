package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.GameEntity
import com.example.data.SaveStateEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveStateScreen(
    games: List<GameEntity>,
    getSaveStates: (Long) -> kotlinx.coroutines.flow.Flow<List<SaveStateEntity>>,
    onCreateSaveState: (Long, String, Int, String) -> Unit,
    onDeleteSaveState: (Long) -> Unit,
    onNavigateTab: (String) -> Unit
) {
    var selectedGameId by remember { mutableStateOf(games.firstOrNull()?.id ?: 1L) }
    val selectedGame = games.find { it.id == selectedGameId } ?: games.firstOrNull()

    val saveStatesFlow = remember(selectedGameId) { getSaveStates(selectedGameId) }
    val saveStates by saveStatesFlow.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Save State") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateTab("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Pilih Game", fontWeight = FontWeight.Bold)

            // Game dropdown selector chips
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                games.forEach { g ->
                    FilterChip(
                        selected = g.id == selectedGameId,
                        onClick = { selectedGameId = g.id },
                        label = { Text(g.title.take(15) + "...") }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Slot Save State (${selectedGame?.title ?: ""})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        selectedGame?.let { game ->
                            val slot = (saveStates.size + 1).coerceIn(1, 5)
                            onCreateSaveState(game.id, game.title, slot, "Manual Save State Slot $slot")
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Buat Save Baru")
                }
            }

            if (saveStates.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("Belum ada Save State untuk game ini.", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(saveStates) { state ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Slot ${state.slotNumber}: ${state.title}", fontWeight = FontWeight.Bold)
                                    val dateStr = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(state.timestamp))
                                    Text(dateStr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                IconButton(onClick = { onDeleteSaveState(state.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
