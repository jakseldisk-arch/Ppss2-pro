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
import androidx.compose.ui.unit.sp
import com.example.data.AppSettingsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphicsSettingsScreen(
    settings: AppSettingsEntity,
    onUpdateSettings: (AppSettingsEntity) -> Unit,
    onNavigateTab: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Grafis & NDK") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateTab("home") }) {
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
            // Preset 2GB RAM Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Text("Mode Performa 2 GB RAM", fontWeight = FontWeight.Bold)
                            }
                            Switch(
                                checked = settings.performanceMode2Gb,
                                onCheckedChange = { onUpdateSettings(settings.copy(performanceMode2Gb = it)) }
                            )
                        }
                        Text(
                            text = "Secara otomatis menurunkan beban rendering, mengaktifkan Skip Draw ringan, dan menyesuaikan cache tekstur agar stabil di perangkat RAM 2 GB.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            item { Text("Resolusi & Rendering", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) }

            // Resolution Scale
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Resolusi Rendering Internal", fontWeight = FontWeight.Bold)
                        Text("Pilih 1.0x (Native) untuk stabilitas maksimum pada perangkat 32-bit.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("0.75x (Ringan)", "1.0x (Native)", "1.5x (HD)", "2.0x (FullHD)").forEach { res ->
                                FilterChip(
                                    selected = settings.resolutionScale == res,
                                    onClick = { onUpdateSettings(settings.copy(resolutionScale = res)) },
                                    label = { Text(res, fontSize = 11.sp) }
                                )
                            }
                        }
                    }
                }
            }

            // Frame Limiter & VSync
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Performa & Sinkronisasi", fontWeight = FontWeight.Bold)
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Batasi Kecepatan Frame (Frame Limiter)", fontWeight = FontWeight.Bold)
                                Text("Menjaga kecepatan game tetap stabil 60 FPS.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = settings.frameLimiter,
                                onCheckedChange = { onUpdateSettings(settings.copy(frameLimiter = it)) }
                            )
                        }

                        Divider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("VSync (Sinkronisasi Vertikal)", fontWeight = FontWeight.Bold)
                                Text("Mencegah screen tearing pada layar perangkat.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = settings.vSync,
                                onCheckedChange = { onUpdateSettings(settings.copy(vSync = it)) }
                            )
                        }

                        Divider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Skip Draw (Lewati Render Berat)", fontWeight = FontWeight.Bold)
                                Text("Membantu menaikkan FPS pada adegan game yang berat.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(
                                checked = settings.skipDraw,
                                onCheckedChange = { onUpdateSettings(settings.copy(skipDraw = it)) }
                            )
                        }
                    }
                }
            }

            // Texture Filtering
            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Penyaringan Tekstur (Texture Filtering)", fontWeight = FontWeight.Bold)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Nearest", "Linear", "Anisotropic").forEach { filter ->
                                FilterChip(
                                    selected = settings.textureFiltering == filter,
                                    onClick = { onUpdateSettings(settings.copy(textureFiltering = filter)) },
                                    label = { Text(filter) }
                                )
                            }
                        }
                    }
                }
            }

            // Sub-navigation buttons to other settings
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { onNavigateTab("controls") }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Gamepad, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Kontrol")
                    }
                    Button(onClick = { onNavigateTab("audio") }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.VolumeUp, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Audio")
                    }
                    Button(onClick = { onNavigateTab("memory") }, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Storage, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Memory")
                    }
                }
            }
        }
    }
}
