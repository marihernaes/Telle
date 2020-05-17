package com.example.telle.data

import androidx.room.*
import java.util.Date

/**
 * A table of an entity (as whole)
 * TODO divide into sub-database? Allow information about single days
 */
@Entity
data class Episode(
    @ColumnInfo(name = "start_date") val startDate: Date,
    @ColumnInfo(name = "end_date") val endDate: Date,
    @ColumnInfo(name = "duration_days") val durationDays: Int? = null,
    @ColumnInfo(name = "cycle_days") val cycleDays: Int? = null,
    @ColumnInfo(name = "mood_happy") val moodHappy: Boolean,
    @ColumnInfo(name = "mood_neutral") val moodNeutral: Boolean,
    @ColumnInfo(name = "mood_sad") val moodSad: Boolean,
    // Flow: 1 is light, 2 is medium, 3 is heavy. Todo: Change to choose for single days
    @ColumnInfo(name = "episode_flow") val episodeFlow: Int,
    @ColumnInfo(name = "episode_notes") val episodeNotes: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
}