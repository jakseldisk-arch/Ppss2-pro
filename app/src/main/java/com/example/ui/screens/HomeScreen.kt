package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppSettingsEntity
import com.example.data.GameEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    games: List<GameEntity>,
    settings: AppSettingsEntity,
    onNavigate: (String) -> Unit,
    onStartGame: (GameEntity) -> Unit,
    onScanGames: () -> Unit,
    isScanning: Boolean,
    scanMessage: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PPSS2 Pro",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Badge(containerColor = MaterialTheme.colorScheme.primary) {
                            Text("Android 9 32-bit", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text(
                        text = "Emulator PS2 Ringan & Stabil untuk Perangkat RAM 2 GB",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Mode RAM 2GB: ${if (settings.performanceMode2Gb) "Aktif (Otomatis)" else "Standار"}", style = MaterialTheme.typography.labelSmall)
                        Text("Mesin NDK C++: Optimal", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Scan Status Banner if scanning
        if (isScanning) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Text(scanMessage, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Quick Feature Cards Grid
        item {
            Text(
                text = "Menu Utama & Pengaturan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Search,
                    title = "Pemindai Game",
                    subtitle = "Cari file ISO/BIN",
                    onClick = { onScanGames() }
                )
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Tune,
                    title = "Grafis & NDK",
                    subtitle = "Resolusi & Skip Draw",
                    onClick = { onNavigate("graphics") }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Gamepad,
                    title = "Kontrol Virtual",
                    subtitle = "Atur tombol PS2",
                    onClick = { onNavigate("controls") }
                )
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.VolumeUp,
                    title = "Audio Emulator",
                    subtitle = "Kualitas Suara",
                    onClick = { onNavigate("audio") }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Storage,
                    title = "Memory Card",
                    subtitle = "Slot 1 & Slot 2",
                    onClick = { onNavigate("memory") }
                )
                QuickMenuCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Save,
                    title = "Save State",
                    subtitle = "Simpan & Muat",
                    onClick = { onNavigate("savestate") }
                )
            }
        }

        // Recent Games Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Game Terakhir Dimainkan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { onNavigate("games") }) {
                    Text("Lihat Semua")
                }
            }
        }

        if (games.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.SportsEsports, contentDescription = null, modifier = Modifier.size(48.dp))
                        Text("Belum ada game PS2 ditemukan", fontWeight = FontWeight.Bold)
                        Text("Gunakan pemindai untuk mencari file ISO/BIN di perangkat Anda.", style = MaterialTheme.typography.bodySmall)
                        Button(onClick = { onScanGames() }) {
                            Text("Pindai Sekarang")
                        }
                    }
                }
            }
        } else {
            items(games.take(3)) { game ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onStartGame(game) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(56.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.SportsEsports, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = game.title, fontWeight = FontWeight.Bold, maxLines = 1)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "ISO • ${game.fileSize}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "Waktu Main: ${game.playTimeMinutes} menit", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        }
                        Button(
                            onClick = { onStartGame(game) },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Main")
                        }
                    }
                }
            }
        }

        // Architecture Note
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Text("Catatan Arsitektur NDK C++", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(
                        text = "PPSS2 Pro menggunakan engine emulasi native berbasis C++ dan integrasi Android NDK (dukungan armeabi-v7a 32-bit) untuk mencapai performa tinggi dan stabil pada perangkat Android 9 dengan RAM 2 GB.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun QuickMenuCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
            Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
