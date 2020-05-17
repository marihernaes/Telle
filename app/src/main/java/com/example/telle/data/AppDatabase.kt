package com.example.telle.data

import android.content.Context
import androidx.room.*
import java.util.*

/**
 * The Room database for this app
 */
@Database(entities = [Episode::class], version = 1, exportSchema = false)  // exportSchema set to false due to issues when changing the database TODO why?
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao

    companion object {
        // From https://android.jlelse.eu/android-room-using-kotlin-f6cc0a05bf23
        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "episode-database.db"
        ).build()
    }
}

/**
 * A type converter for the Date values, in order to save these to the database
 * https://developer.android.com/training/data-storage/room/referencing-data
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}