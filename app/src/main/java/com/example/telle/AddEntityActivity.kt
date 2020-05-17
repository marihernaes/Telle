package com.example.telle

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.telle.data.AppDatabase
import com.example.telle.data.Episode
import com.example.telle.data.EpisodeDao
import com.example.telle.utilities.TimeUtils
import com.example.telle.utilities.TimeUtils.calculateDuration
import com.example.telle.utilities.TimeUtils.stringToDate
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_display.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddEntityActivity : AppCompatActivity(), DatePickerFragment.OnChosenDateListener {
    private var editStartDate: EditText? = null
    private var editEndDate: EditText? = null
    private var editNotes: EditText? = null
    private var editMood: ChipGroup? = null
    private var editFlow: ChipGroup? = null
    private var startDateFlag: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entity)

        // Fetch the input field views
        // TODO change to use data binding... FindViewById is expensive
        editStartDate = findViewById(R.id.editStartDate)
        editEndDate = findViewById(R.id.editEndDate)
        editFlow = findViewById(R.id.chip_group_flow)
        editMood = findViewById(R.id.chip_group_mood)
        editNotes = findViewById(R.id.editNotes)
        onAttachFragment(DatePickerFragment())
    }

    // Attach to the DatePickerFragment
    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is DatePickerFragment) {
            fragment.setOnChosenDateListener(this)  // Set smart cast
        }
    }

    // Show the DatePickerDialog when this function is called from the EditText field
    fun showStartDatePickerDialog(v: View) {
        val newDatePickerFragment = DatePickerFragment()
        startDateFlag = true
        newDatePickerFragment.show(supportFragmentManager, "datePicker")
    }

    fun showEndDatePickerDialog(v: View) {
        val newDatePickerFragment = DatePickerFragment()
        startDateFlag = false
        newDatePickerFragment.show(supportFragmentManager, "datePicker")
    }

    // Fetch the data from the DatePickerFragment when "onDateSelected" is called there
    override fun onDateSelected(str: String) {
        // The user chose a date in the DatePickerFragment
        if (startDateFlag == true) {  // Either a start date
            editStartDate?.setText(str)
        } else {  // Or an end date
            editEndDate?.setText(str)
        }
    }

    // Inflate the menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_entity_menu, menu)
        return true
    }

    // When "save" or "delete" is chosen in the options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                // Save episode to database
                insertEpisode()
                // Exit activity
                finish()
                return true
            }
            R.id.action_delete -> {
                // Exit without saving
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertEpisode() {
        // TODO LOAD EPISODE DAO BETTER
        val episodeDao = AppDatabase(this).episodeDao()
        // TODO AVOID THE GLOBAL SCOPE TRY TRY CATCH BLOCK!!!! DO THE SAME IN MAIN ACTIVITY
        GlobalScope.launch {
            val startDate = editStartDate?.text.toString().trim()
            val endDate = editEndDate?.text.toString().trim()
            val newEpisode = Episode(
                stringToDate(startDate),
                stringToDate(endDate),
                calculateDuration(stringToDate(startDate), stringToDate(endDate)),
                moodHappy = false,  // TODO CHANGE HARD CODED VALUES :-)
                moodNeutral = true,
                moodSad = false,
                episodeFlow = getFlow(),
                episodeNotes = editNotes?.text.toString().trim()
            )
            episodeDao.insertOne(newEpisode)

            // TODO REMOVE USE REPOSITORY INSTEAD?
            val recentEpisode = newEpisode
            val dateSortedEpisodes : List<Episode> = episodeDao.getAllByDateAsc()
            // Find the index in the ascending ([02.2020, 04.2020, 05.2020...]) list
            val recentIndex = dateSortedEpisodes.indexOf(recentEpisode)
            if (recentIndex > 0) {
                val prevEpisode = dateSortedEpisodes[recentIndex - 1]
                // Update the recent cycle
                episodeDao.updateCycle(recentEpisode.id,
                    calculateDuration(prevEpisode.startDate, recentEpisode.startDate)
                )
            }
            // If the recent episode is not the newest one
            if (recentIndex + 1 < dateSortedEpisodes.size) {
                val nextEpisode = dateSortedEpisodes[recentIndex + 1]
                // Update the cycle of the following episode
                episodeDao.updateCycle(nextEpisode.id,
                    calculateDuration(recentEpisode.startDate, nextEpisode.startDate)
                )
            }

        }

        Toast.makeText(
            this, "Episode added!", Toast.LENGTH_SHORT).show()
    }

    private fun getFlow(): Int {
        return when (editFlow?.checkedChipId) {
            R.id.chipLight -> {
                1
            }
            R.id.chipMedium -> {
                2
            }
            else -> {
                3
            }
        }
    }

}
