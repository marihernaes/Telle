package com.example.telle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telle.data.Episode
import com.example.telle.data.EpisodeRepository

/**
 * The ViewModel used in [HomeFragment].
 */
class HomeViewModel(val episodeRepository: EpisodeRepository) : ViewModel() {

    // val emptyDatabase = episodeRepository.emptyDatabase()
    // TODO research how to do avoid these when the database is empty..
    val avgCycle = episodeRepository.getAverageCycle()
    val episodeWithLastStart = episodeRepository.getLatestLiveEpisode()
}