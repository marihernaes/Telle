package com.example.telle.ui.display

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telle.data.Episode
import com.example.telle.data.EpisodeRepository

/**
 * The ViewModel used in [DisplayFragment].
 */
class DisplayViewModel(episodeRepository: EpisodeRepository) : ViewModel() {
    val avgDuration = episodeRepository.getAverageDuration()
    val avgCycle = episodeRepository.getAverageCycle()
    val liveEpisodeIds : LiveData<List<Episode>> = episodeRepository.getAllLiveByDate()
}