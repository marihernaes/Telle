package com.example.telle.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telle.data.EpisodeRepository

/**
 * Factory for creating a [MeViewModel] with a constructor that takes an [EpisodeRepository].
 */
class MeViewModelFactory(
    private val repository: EpisodeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeViewModel(repository) as T
    }
}