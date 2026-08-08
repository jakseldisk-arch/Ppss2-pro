package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val filePath: String,
    val fileSize: String,
    val coverUrl: String = "",
    val isFavorite: Boolean = false,
    val playTimeMinutes: Int = 0,
    val lastPlayedTimestamp: Long = System.currentTimeMillis()
)
