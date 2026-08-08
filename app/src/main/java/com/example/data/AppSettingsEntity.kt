package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val resolutionScale: String = "1.5x", // "1x", "1.5x", "2x"
    val frameLimiter: Boolean = true,
    val vSync: Boolean = true,
    val skipDraw: Boolean = false,
    val textureFiltering: String = "Bilinear", // "Bilinear", "Nearest", "Anisotropic"
    val performanceMode2Gb: Boolean = true,
    val virtualPadOpacity: Float = 0.7f,
    val vibrationEnabled: Boolean = true,
    val audioQuality: String = "Sedang (Medium)"
)
