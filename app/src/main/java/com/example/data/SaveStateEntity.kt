package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_states")
data class SaveStateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val gameId: Long,
    val gameTitle: String,
    val slotNumber: Int, // 1 to 5
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
    val previewImagePath: String = ""
)
