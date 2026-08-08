package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GameEntity::class, AppSettingsEntity::class, MemoryCardEntity::class, SaveStateEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ppss2_pro_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialData(database.appDao())
                    }
                }
            }

            suspend fun populateInitialData(dao: AppDao) {
                dao.insertSettings(AppSettingsEntity(id = 1))
                dao.insertMemoryCards(
                    listOf(
                        MemoryCardEntity(slotNumber = 1, title = "Memory Card Slot 1 (PS2)", usedBytes = 1024L * 1536L, totalBytes = 1024L * 1024L * 8L),
                        MemoryCardEntity(slotNumber = 2, title = "Memory Card Slot 2 (PS2)", usedBytes = 1024L * 512L, totalBytes = 1024L * 1024L * 8L)
                    )
                )
                dao.insertGames(
                    listOf(
                        GameEntity(
                            title = "Gran Turismo 4 (PS2 Classic)",
                            filePath = "/storage/emulated/0/PS2/Gran_Turismo_4.iso",
                            fileSize = "4.2 GB",
                            isFavorite = true,
                            playTimeMinutes = 145,
                            coverUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=500"
                        ),
                        GameEntity(
                            title = "God of War II (PS2)",
                            filePath = "/storage/emulated/0/PS2/God_of_War_II.iso",
                            fileSize = "3.8 GB",
                            isFavorite = true,
                            playTimeMinutes = 210,
                            coverUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=500"
                        ),
                        GameEntity(
                            title = "Resident Evil 4 (PS2)",
                            filePath = "/storage/emulated/0/PS2/Resident_Evil_4.iso",
                            fileSize = "3.2 GB",
                            isFavorite = false,
                            playTimeMinutes = 90,
                            coverUrl = "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?w=500"
                        ),
                        GameEntity(
                            title = "Shadow of the Colossus (PS2)",
                            filePath = "/storage/emulated/0/PS2/Shadow_Colossus.iso",
                            fileSize = "2.5 GB",
                            isFavorite = true,
                            playTimeMinutes = 60,
                            coverUrl = "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=500"
                        )
                    )
                )
            }
        }
    }
}
