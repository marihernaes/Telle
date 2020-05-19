package com.example.telle.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telle.utilities.TimeUtils

/**
 * Repository module for handling data operations.
 */
class EpisodeRepository private constructor(private val episodeDao: EpisodeDao) {

    fun getAllByID() = episodeDao.getAllByID()
    fun getAllLiveByDate() = episodeDao.getAllByDateDescLive()
    fun getAverageDuration() = episodeDao.getAverageDuration()
    fun getLatestEpisode() = episodeDao.getLatestEpisode()
    fun deleteOne(e: Episode) = episodeDao.delete(e)

    fun getAverageCycle() =  episodeDao.getAverageCycle()
    fun getLatestLiveEpisode() = episodeDao.getLatestLiveEpisode()

    fun getCount() = episodeDao.getCount()

    /**
     * Update the cycle of the recently added episode and the episode just after
     */
    // TODO update AddEntityActivity. When this uses this repositories method insted of its own,
    //  the suggestion "make private" will disappear.
    fun updateCycle(recentEpisode: Episode) {
        val dateSortedEpisodes : List<Episode> = episodeDao.getAllByDateAsc()
        // Find the index in the ascending ([02.2020, 04.2020, 05.2020...]) list
        val recentIndex = dateSortedEpisodes.indexOf(recentEpisode)
        if (recentIndex > 0) {
            val prevEpisode = dateSortedEpisodes[recentIndex - 1]
            // Update the recent cycle
            episodeDao.updateCycle(recentEpisode.id,
                TimeUtils.calculateDuration(prevEpisode.startDate, recentEpisode.startDate)
            )
        }
        // If the recent episode is not the newest one
        if (recentIndex + 1 < dateSortedEpisodes.size) {
            val nextEpisode = dateSortedEpisodes[recentIndex + 1]
            // Update the cycle of the following episode
            episodeDao.updateCycle(nextEpisode.id,
                TimeUtils.calculateDuration(recentEpisode.startDate, nextEpisode.startDate)
            )
        }
    }

    fun recomputeAllCycles() {
        // TODO REMOVE or improve. Quick hack for debugging purposes.
        val dateSortedEpisodes : List<Episode> = episodeDao.getAllByDateAsc()
        for (episode in dateSortedEpisodes) {
            updateCycle(episode)
        }
        // Null out the very first episode
        episodeDao.nullOutCycle(dateSortedEpisodes.first().id)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: EpisodeRepository? = null

        fun getInstance(episodeDao: EpisodeDao) =
            instance ?: synchronized(this) {
                instance ?: EpisodeRepository(episodeDao).also { instance = it }
            }
    }
}