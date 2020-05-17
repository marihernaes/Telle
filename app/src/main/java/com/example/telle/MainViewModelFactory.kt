package com.example.telle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telle.data.EpisodeRepository

/**
 * Factory for creating a [MainViewModel] with a constructor that takes an [EpisodeRepository].
 */
class MainViewModelFactory(
    private val repository: EpisodeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}