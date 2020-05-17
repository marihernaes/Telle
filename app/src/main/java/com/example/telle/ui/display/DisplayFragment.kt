package com.example.telle.ui.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.telle.adapters.EpisodeAdapter
import com.example.telle.databinding.FragmentDisplayBinding
import com.example.telle.utilities.InjectorUtils

/**
 * The DisplayFragment which display entities from the database using data binding
 */
class DisplayFragment : Fragment() {

    private val viewModel: DisplayViewModel by viewModels {
        InjectorUtils.provideDisplayViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // The binding is automatically named in camelcase after fragment_display.xml
        val binding = FragmentDisplayBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = EpisodeAdapter()
        // This is automatically named  after @+id/episode_list_recycler in fragment_display.xml
        binding.episodeListRecycler.adapter = adapter
        subscribeUi(adapter)

        // Setting up LiveData observation relationship like this
        // https://codelabs.developers.google.com/codelabs/kotlin-android-training-live-data/index.html#4
        viewModel.avgDuration.observe(viewLifecycleOwner, Observer { newDuration ->
            // Quick and dirty! Show the int as a string in the layout
            val suffix = if (newDuration == 1) " day" else " days"
            if (newDuration != null) {
                binding.avgDuration = newDuration.toString() + suffix
            } else {
                binding.avgDuration = "Not enough data yet"
            }

        })

        viewModel.avgCycle.observe(viewLifecycleOwner, Observer { newCycle ->
            val suffix = if (newCycle == 1) " day" else " days"
            if (newCycle != null) {
                binding.avgCycle = newCycle.toString() + suffix
            } else {
                binding.avgCycle = "Not enough data yet"
            }

        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
    * Subscribe to an item from [DisplayViewModel] and observe it
     */
    private fun subscribeUi(adapter: EpisodeAdapter) {
        viewModel.liveEpisodeIds.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
