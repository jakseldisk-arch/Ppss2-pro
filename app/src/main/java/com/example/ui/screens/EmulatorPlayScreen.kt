package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppSettingsEntity
import com.example.data.GameEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmulatorPlayScreen(
    game: GameEntity,
    settings: AppSettingsEntity,
    onStopGame: () -> Unit,
    onSaveStateQuick: () -> Unit
) {
    var showPauseMenu by remember { mutableStateOf(false) }
    var fps by remember { mutableStateOf(60) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Simulated Game Render Screen Viewport
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar Overlay
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { showPauseMenu = true }) {
                            Icon(Icons.Default.Pause, contentDescription = "Jeda", tint = Color.White)
                        }
                        Column {
                            Text(text = game.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                            Text(text = "FPS: $fps | Res: ${settings.resolutionScale}", color = Color.Green, fontSize = 11.sp)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { onSaveStateQuick() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save", fontSize = 11.sp)
                        }
                        OutlinedButton(
                            onClick = { onStopGame() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Keluar", fontSize = 11.sp)
                        }
                    }
                }
            }

            // Center Simulation Graphic
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.SportsEsports,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = Color.White.copy(alpha = 0.3f)
                    )
                    Text(
                        text = "SIMULASI EMULASI PS2 [NDK C++ ACTIVE]",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = game.filePath,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }

            // Virtual Gamepad Overlay (PS2 Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // L1 / L2 and R1 / R2 top shoulder buttons simulation row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GamepadButton("L2", alpha = settings.virtualPadOpacity)
                        GamepadButton("L1", alpha = settings.virtualPadOpacity)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GamepadButton("R1", alpha = settings.virtualPadOpacity)
                        GamepadButton("R2", alpha = settings.virtualPadOpacity)
                    }
                }

                // Main Controls (D-Pad Left + Action Right + Center Start/Select)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left D-Pad & Analog
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        GamepadJoystick("ANALOG", alpha = settings.virtualPadOpacity)
                        GamepadDPad(alpha = settings.virtualPadOpacity)
                    }

                    // Center Start / Select
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        GamepadButton("SELECT", alpha = settings.virtualPadOpacity)
                        GamepadButton("START", alpha = settings.virtualPadOpacity)
                    }

                    // Right Action Buttons (Triangle, Square, Cross, Circle)
                    GamepadActionPad(alpha = settings.virtualPadOpacity)
                }
            }
        }

        // Pause Dialog
        if (showPauseMenu) {
            AlertDialog(
                onDismissRequest = { showPauseMenu = false },
                title = { Text("Menu Jeda Emulator") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Game: ${game.title}")
                        Button(onClick = { showPauseMenu = false }, modifier = Modifier.fillMaxWidth()) {
                            Text("Lanjutkan Permainan")
                        }
                        Button(onClick = {
                            onSaveStateQuick()
                            showPauseMenu = false
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Simpan State (Save State)")
                        }
                        OutlinedButton(onClick = {
                            showPauseMenu = false
                            onStopGame()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Keluar ke Menu Utama")
                        }
                    }
                },
                confirmButton = {}
            )
        }
    }
}

@Composable
fun GamepadButton(label: String, alpha: Float) {
    Surface(
        modifier = Modifier.size(if (label.length > 2) 42.dp else 38.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.DarkGray.copy(alpha = alpha)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(label, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GamepadJoystick(label: String, alpha: Float) {
    Surface(
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        color = Color.DarkGray.copy(alpha = alpha),
        border = BorderStroke(2.dp, Color.Gray.copy(alpha = alpha))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = Color.Gray.copy(alpha = alpha)
            ) {}
        }
    }
}

@Composable
fun GamepadDPad(alpha: Float) {
    Box(
        modifier = Modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .width(24.dp)
                .height(72.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color.DarkGray.copy(alpha = alpha)
        ) {}
        Surface(
            modifier = Modifier
                .width(72.dp)
                .height(24.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color.DarkGray.copy(alpha = alpha)
        ) {}
    }
}

@Composable
fun GamepadActionPad(alpha: Float) {
    Box(
        modifier = Modifier.size(88.dp),
        contentAlignment = Alignment.Center
    ) {
        // Triangle (Top)
        Box(modifier = Modifier.align(Alignment.TopCenter)) { GamepadActionBtn("△", Color.Green, alpha) }
        // Square (Left)
        Box(modifier = Modifier.align(Alignment.CenterStart)) { GamepadActionBtn("□", Color.Magenta, alpha) }
        // Circle (Right)
        Box(modifier = Modifier.align(Alignment.CenterEnd)) { GamepadActionBtn("◯", Color.Red, alpha) }
        // Cross (Bottom)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) { GamepadActionBtn("✕", Color.Blue, alpha) }
    }
}

@Composable
fun GamepadActionBtn(symbol: String, color: Color, alpha: Float) {
    Surface(
        modifier = Modifier.size(32.dp),
        shape = CircleShape,
        color = color.copy(alpha = alpha * 0.8f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(symbol, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}
