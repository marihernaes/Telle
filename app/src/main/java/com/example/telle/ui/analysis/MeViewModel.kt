package com.example.telle.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telle.data.EpisodeRepository

/**
 * The ViewModel used in [MeFragment].
 */
class MeViewModel(episodeRepository: EpisodeRepository) : ViewModel() {

    val liveEpisodeIds = episodeRepository.getAllByID()
}