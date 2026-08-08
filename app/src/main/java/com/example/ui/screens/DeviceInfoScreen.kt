package com.example.ui.screens

import android.os.Build
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceInfoScreen(onNavigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Informasi Perangkat & Mesin") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateHome() }) {
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
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Status Kompatibilitas PPSS2 Pro", fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "Aplikasi ini dioptimalkan khusus untuk Android 9 dengan dukungan arsitektur 32-bit (armeabi-v7a) dan RAM minimum 2 GB.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            item {
                Card {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Spesifikasi Sistem Perangkat", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                        InfoRow(label = "Versi Android", value = "Android 9.0 (Pie) - API ${Build.VERSION.SDK_INT}")
                        Divider()
                        InfoRow(label = "Arsitektur Prosesor", value = "ARMv7 32-bit (armeabi-v7a)")
                        Divider()
                        InfoRow(label = "RAM Minimum", value = "2 GB (Mode Performa Aktif)")
                        Divider()
                        InfoRow(label = "Render Grafik", value = "OpenGL ES 3.0 / Hardware Accelerated")
                        Divider()
                        InfoRow(label = "Mesin Emulasi", value = "PPSS2 Native C++ Core & NDK Integration")
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Keamanan & Legalitas", fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "PPSS2 Pro berjalan sepenuhnya secara lokal di perangkat tanpa memerlukan akses root. Pastikan Anda memiliki file game fisik dan BIOS PS2 secara legal.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
