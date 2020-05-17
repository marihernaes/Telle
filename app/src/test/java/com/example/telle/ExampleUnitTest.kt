package com.example.telle

import com.example.telle.utilities.TimeUtils.calculateDuration
import com.example.telle.utilities.TimeUtils.numberStringToDate
import com.example.telle.utilities.TimeUtils.stringToDate
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun calculate_duration_isCorrect() {
        val startDate = numberStringToDate("2020-04-14")
        val endDate = numberStringToDate("2020-04-18")
        assertEquals(4, calculateDuration(startDate, endDate))
        assertEquals(-4, calculateDuration(endDate, startDate))
    }

    @Test
    fun calculate_noDuration_isCorrect() {
        val startDate = numberStringToDate("2020-04-14")
        assertEquals(0, calculateDuration(startDate,startDate))
    }
}
