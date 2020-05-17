package com.example.telle.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Static methods used to set date format, calculate the
 * difference in days between two dates, calculate the length of a cycle
 *
 * NOTE: USING LOCALDATE AND PERIOD.BETWEEN WOULD BE EASIER, BUT IT REQUIRES API LEVEL >21.
 * THEREFORE, THE FUNCTIONS ARE (UNTIL NOW) WRITTEN MANUALLY.
 * SEE https://www.baeldung.com/kotlin-dates FOR THE FUTURE.
 */

@SuppressLint("SimpleDateFormat")
val sdf = SimpleDateFormat("dd MMM yyyy") // Different than default

object TimeUtils {

    // 1 millisecond is 0.001 seconds is 1/60 minute is 1/60 hours is 1/24 days
    private const val oneDay = (1000 * 60 * 60 * 24)

    fun dateToString(date: Date): String {
        return sdf.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToShortString(date: Date): String {
        return SimpleDateFormat("dd MMM").format(date)
    }

    fun stringToDate(s: String): Date {
        // Converts "04. Apr 2020" to date
        return sdf.parse(s)
    }

    @SuppressLint("SimpleDateFormat")
    fun numberStringToDate(s: String): Date {
        // Converts "2020-04-14" to date
        return SimpleDateFormat("yyyy-MM-dd").parse(s)
    }

    /**
     * Calculate duration in days between two days
     */
    fun calculateDuration(startDate: Date, endDate: Date): Int {
        val startTime = startDate.time
        val endTime = endDate.time
        return ((endTime - startTime) / oneDay).toInt()
    }

    /**
     * From a date and a number of days, calculate the new date when adding this number of dates
     */
    fun datePlusDays(startDate: Date, cycle: Int): Date {
        return Date(startDate.time + (oneDay * cycle))
    }

    /**
     * Returns number of days until a given date, or the number of days since date
     */
    fun daysUntilDate(targetDate: Date): Int {
        val currentDate = Calendar.getInstance().timeInMillis
        return ((targetDate.time - currentDate) / oneDay).toInt()
    }
}
