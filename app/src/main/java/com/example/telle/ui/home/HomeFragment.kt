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

        viewModel.avgCycle.observe(viewLifecycleOwner, Observer {
            val estimatedCycle = it
            viewModel.episodeWithLastStart.observe(viewLifecycleOwner, Observer { it ->
                val estimatedUpcomingStartDate = datePlusDays(it.startDate, estimatedCycle)
                val estimatedDays = daysUntilDate(estimatedUpcomingStartDate)
                // TODO MAKE TWO TEXT FIELDS IN THE DATA BINDING - AND ADD A DIVIDER
                // Set the text with information about estimated new period
                var daysString = ""
                daysString = if (estimatedDays < 0) {
                    ((-1) * estimatedDays).toString() + " days due\n"
                } else {
                    estimatedDays.toString() + " days left\n"
                }
                // TODO add special case if the estimation is "TODAY".
                binding.debugText = daysString +
                        "Next period\n" + dateToShortString(estimatedUpcomingStartDate) +
                        "\n(average cycle: " + (estimatedCycle).toString() + " days)"
                // Set the progress bar
                binding.progressMax = estimatedCycle
                binding.progressValue = estimatedCycle - estimatedDays
            })
        })

        return binding.root
    }

}
