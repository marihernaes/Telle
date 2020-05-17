package com.example.telle.utilities

import android.content.Context
import com.example.telle.MainViewModelFactory
import com.example.telle.data.AppDatabase
import com.example.telle.data.EpisodeRepository
import com.example.telle.ui.analysis.MeViewModel
import com.example.telle.ui.analysis.MeViewModelFactory
import com.example.telle.ui.display.DisplayViewModelFactory
import com.example.telle.ui.home.HomeViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getEpisodeRepository(context: Context): EpisodeRepository {
        return EpisodeRepository.getInstance(
            AppDatabase.invoke(context.applicationContext).episodeDao())
    }

    fun provideDisplayViewModelFactory(
        context: Context
    ): DisplayViewModelFactory {
        val repository = getEpisodeRepository(context)
        return DisplayViewModelFactory(repository)
    }

    fun provideHomeViewModelFactory(
        context: Context
    ): HomeViewModelFactory {
        val repository = getEpisodeRepository(context)
        return HomeViewModelFactory(repository)
    }

    fun provideMeViewModelFactory(
        context: Context
    ): MeViewModelFactory {
        val repository = getEpisodeRepository(context)
        return MeViewModelFactory(repository)
    }

    fun provideMainViewModelFactory(
        context: Context
    ): MainViewModelFactory {
        val repository = getEpisodeRepository(context)
        return MainViewModelFactory(repository)
    }
}