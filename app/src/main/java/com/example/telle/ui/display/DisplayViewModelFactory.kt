package com.example.telle.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telle.data.EpisodeRepository

/**
 * Factory for creating a [DisplayViewModel] with a constructor that takes an [EpisodeRepository].
 */
class DisplayViewModelFactory(
    private val repository: EpisodeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DisplayViewModel(repository) as T
    }

}