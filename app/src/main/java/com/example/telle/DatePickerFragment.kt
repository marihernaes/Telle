package com.example.telle

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.telle.utilities.TimeUtils.dateToString
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var callback: OnChosenDateListener

    fun setOnChosenDateListener(callback: OnChosenDateListener) {
        this.callback = callback
    }

    // The container Activity must implement this interface so the frag can deliver messages
    interface OnChosenDateListener {
        fun onDateSelected(str: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Fetch the date chosen by the user
        val c = Calendar.getInstance()
        c.set(year, month, day)
        // Use TimeUtils.kt to format the date to a string
        val formattedDate = dateToString(c.time)
        // Send the event to the host activity
        callback.onDateSelected(formattedDate)
    }
}