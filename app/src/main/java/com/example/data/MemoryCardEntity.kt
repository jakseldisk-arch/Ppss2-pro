package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_cards")
data class MemoryCardEntity(
    @PrimaryKey val slotNumber: Int, // 1 or 2
    val title: String,
    val usedBytes: Long = 1024L * 512L,
    val totalBytes: Long = 1024L * 1024L * 8L // 8 MB
)
