package com.example.hahalolofake.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hahalolofake.data.dao.EffectSoundDao
import com.example.hahalolofake.data.models.EffectSound

@Database(
    entities = [EffectSound::class],
    version = 1,
    exportSchema = false
)
abstract class EffectSoundDatabase: RoomDatabase() {
    abstract fun effectDao(): EffectSoundDao

    companion object {
        private const val DATABASE_NAME = "volume_database"

        // Define a migration from version 1 to version 2
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Define the SQL statements to perform the migration.
                // For example, you can add or modify tables here.
                // Refer to the Room documentation for details on writing migrations.
            }
        }

        @Volatile
        private var INSTANCE: EffectSoundDatabase? = null

        fun getDatabase(context: Context): EffectSoundDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EffectSoundDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2) // Add your migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}