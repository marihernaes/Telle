package com.example.telle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telle.data.Episode
import com.example.telle.data.EpisodeRepository

/**
 * The ViewModel used in [HomeFragment].
 */
class HomeViewModel(episodeRepository: EpisodeRepository) : ViewModel() {

    val avgCycle = episodeRepository.getAverageCycle()
    val episodeWithLastStart = episodeRepository.getLatestLiveEpisode()

    val liveEpisodeIds : LiveData<List<Episode>> = episodeRepository.getAllByID()
}