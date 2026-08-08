package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(currentTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Beranda") },
            label = { Text("Beranda") },
            selected = currentTab == "home",
            onClick = { onTabSelected("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Gamepad, contentDescription = "Daftar Game") },
            label = { Text("Game") },
            selected = currentTab == "games",
            onClick = { onTabSelected("games") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Pengaturan") },
            label = { Text("Pengaturan") },
            selected = currentTab == "graphics" || currentTab == "controls" || currentTab == "audio" || currentTab == "memory" || currentTab == "savestate",
            onClick = { onTabSelected("graphics") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info Perangkat") },
            label = { Text("Perangkat") },
            selected = currentTab == "device",
            onClick = { onTabSelected("device") }
        )
    }
}
