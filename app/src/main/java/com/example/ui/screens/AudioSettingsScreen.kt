package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppSettingsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioSettingsScreen(
    settings: AppSettingsEntity,
    onUpdateSettings: (AppSettingsEntity) -> Unit,
    onNavigateTab: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Audio") },
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
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Kualitas Suara Emulator", fontWeight = FontWeight.Bold)
                        Text("Pilih tingkat kualitas audio untuk mengoptimalkan penggunaan CPU pada perangkat RAM 2 GB.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        listOf("Rendah (Ringan)", "Sedang (Medium)", "Tinggi (High Quality)").forEach { quality ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                FilterChip(
                                    selected = settings.audioQuality == quality,
                                    onClick = { onUpdateSettings(settings.copy(audioQuality = quality)) },
                                    label = { Text(quality) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
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
                    Button(onClick = { onNavigateTab("controls") }, modifier = Modifier.weight(1f)) {
                        Text("Kontrol")
                    }
                    Button(onClick = { onNavigateTab("memory") }, modifier = Modifier.weight(1f)) {
                        Text("Memory Card")
                    }
                }
            }
        }
    }
}
