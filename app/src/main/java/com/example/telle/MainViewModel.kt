package com.example.telle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.telle.data.Episode
import com.example.telle.data.EpisodeRepository

/**
 * The ViewModel used in [MainActivity].
 */
class MainViewModel(val episodeRepository: EpisodeRepository) : ViewModel() {
    // TODO ADD WHAT NEEDED
    val latestEpisode = episodeRepository.getLatestEpisode()

}