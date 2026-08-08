package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppSettingsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlSettingsScreen(
    settings: AppSettingsEntity,
    onUpdateSettings: (AppSettingsEntity) -> Unit,
    onNavigateTab: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Kontrol Virtual") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateTab("graphics") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("Transparansi Tombol Virtual", fontWeight = FontWeight.Bold)
                        Text("Opasitas: ${(settings.virtualPadOpacity * 100).toInt()}%")
                        Slider(
                            value = settings.virtualPadOpacity,
                            onValueChange = { onUpdateSettings(settings.copy(virtualPadOpacity = it)) },
                            valueRange = 0.3f..1.0f
                        )

                        Divider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Getaran Sentuhan (Vibration)", fontWeight = FontWeight.Bold)
                                Text("Memberikan umpan balik haptik saat tombol ditekan.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = settings.vibrationEnabled,
                                onCheckedChange = { onUpdateSettings(settings.copy(vibrationEnabled = it)) }
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { onNavigateTab("graphics") }, modifier = Modifier.weight(1f)) {
                        Text("Grafis")
                    }
                    Button(onClick = { onNavigateTab("audio") }, modifier = Modifier.weight(1f)) {
                        Text("Audio")
                    }
                    Button(onClick = { onNavigateTab("memory") }, modifier = Modifier.weight(1f)) {
                        Text("Memory Card")
                    }
                }
            }
        }
    }
}
