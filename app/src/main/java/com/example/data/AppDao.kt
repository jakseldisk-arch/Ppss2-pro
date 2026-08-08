package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM games ORDER BY lastPlayedTimestamp DESC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Update
    suspend fun updateGame(game: GameEntity)

    @Query("DELETE FROM games WHERE id = :id")
    suspend fun deleteGame(id: Long)

    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<AppSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: AppSettingsEntity)

    @Query("SELECT * FROM memory_cards")
    fun getMemoryCards(): Flow<List<MemoryCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemoryCards(cards: List<MemoryCardEntity>)

    @Query("SELECT * FROM save_states WHERE gameId = :gameId ORDER BY slotNumber ASC")
    fun getSaveStates(gameId: Long): Flow<List<SaveStateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaveState(saveState: SaveStateEntity)

    @Query("DELETE FROM save_states WHERE id = :id")
    suspend fun deleteSaveState(id: Long)
}
