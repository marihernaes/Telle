package com.example.telle.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the Pet class.
 */
@Dao
interface EpisodeDao {
    // Getters
    @Query("SELECT COUNT(*) FROM episode")
    fun getCount(): LiveData<Int>

    @Query("SELECT * FROM episode ORDER BY start_date ASC")
    fun getAllByDateAsc(): List<Episode>

    @Query("SELECT * FROM episode ORDER BY start_date DESC")
    fun getAllByDateDescLive(): LiveData<List<Episode>>

    @Query("SELECT * FROM episode ORDER BY _id")
    fun getAllByID(): LiveData<List<Episode>>

    // More complex queries
    @Query("SELECT AVG(duration_days) FROM episode") // Null is excluded by default
    fun getAverageDuration(): LiveData<Int>

    @Query("SELECT AVG(cycle_days) FROM episode")
    fun getAverageCycle(): LiveData<Int>

    @Query("SELECT * FROM episode ORDER BY start_date DESC LIMIT 1 ")
    fun getLatestLiveEpisode(): LiveData<Episode>

    @Query("SELECT * FROM episode ORDER BY start_date DESC LIMIT 1 ")
    fun getLatestEpisode(): Episode

    // Insert and delete
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(episode: Episode)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg episodes: Episode)

    @Delete
    fun delete(episode: Episode)

   // Update the cycle value
    @Query("UPDATE episode SET cycle_days = :cd WHERE _id = :id")
    fun updateCycle(id: Long, cd: Int)

    // Null out the cycle value
    @Query("UPDATE episode SET cycle_days = null WHERE _id = :id")
    fun nullOutCycle(id: Long)
}