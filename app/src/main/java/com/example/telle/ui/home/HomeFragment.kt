package com.example.telle.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.telle.databinding.FragmentHomeBinding
import com.example.telle.utilities.InjectorUtils
import com.example.telle.utilities.TimeUtils.calculateDuration
import com.example.telle.utilities.TimeUtils.datePlusDays
import com.example.telle.utilities.TimeUtils.dateToShortString
import com.example.telle.utilities.TimeUtils.daysUntilDate

/**
 * The HomeFragment which shows the front page and the circular progress bar using data binding
 */
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // The binding is automatically named in camelcase after fragment_home.xml
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // TODO remove or document hard-coded values
        // TODO or make it possible to choose the standard under "me"
        var estimatedCycle = 28  // Biological standard cycle
        var estimatedWaitingTime = estimatedCycle // Default to day zero in period

        viewModel.numElements.observe(viewLifecycleOwner, Observer { numElements ->
            // TODO add string information when the database is empty
            if (numElements > 0) {
                // If the database contains only one element, the average cycle is null
                viewModel.avgCycle.observe(viewLifecycleOwner, Observer {avgCycle ->
                    if (avgCycle != null) {
                        estimatedCycle = avgCycle
                    }
                })
                // Based on the episode with the latest start date, estimate the next period
                viewModel.episodeWithLastStart.observe(viewLifecycleOwner, Observer {
                    estimatedWaitingTime =
                        daysUntilDate(datePlusDays(it.startDate, estimatedCycle))

                    // Set the string depending on the number of days
                    val daysString = if (estimatedWaitingTime < 0) {
                        ((-1) * estimatedWaitingTime).toString() + " days due\n"
                    } else {
                        estimatedWaitingTime.toString() + " days left\n"
                    }
                    // TODO add special case if the estimation is "TODAY".
                    // TODO MAKE TWO TEXT FIELDS IN THE DATA BINDING - AND ADD A DIVIDER
                    binding.debugText = daysString + "Next period\n" +
                            dateToShortString(datePlusDays(it.startDate, estimatedCycle)) +
                            "\n(average cycle: " + (estimatedCycle).toString() + " days)"

                    // Set the progress bar
                    binding.progressMax = estimatedCycle
                    binding.progressValue = estimatedCycle - estimatedWaitingTime
                })

            }
        })

        return binding.root
    }

}
