package com.example.testapplication.helperDate

import java.text.SimpleDateFormat
import java.util.*

object DateParsing {

    private const val HOURS_MINUTES = "hh:mm"
    private const val DAY_MONTH_YEAR = "dd.MM.yyyy"

    fun getFormattedDate(noteDate: Date): String {

        val dataCalendar = Calendar.getInstance()
        dataCalendar.time = noteDate


        val defaultDate: SimpleDateFormat

        val currentData = Calendar.getInstance()

        defaultDate = if (
            currentData.get(Calendar.DATE) >= dataCalendar.get(Calendar.DATE) &&
            currentData.get(Calendar.DAY_OF_WEEK) > dataCalendar.get(Calendar.DAY_OF_WEEK)
        ) {
            SimpleDateFormat(DAY_MONTH_YEAR)
        } else SimpleDateFormat(HOURS_MINUTES)


        return defaultDate.format(dataCalendar.time)

    }

}