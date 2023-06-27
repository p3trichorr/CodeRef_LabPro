package com.example.labproject2

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

fun getTodayDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDatePassed(debtDate: String): Int {
    val dateString = getTodayDate()
    val dateSplit = dateString.split("-")
    val debtDateSplit = debtDate.split("-")
    val startDate = LocalDate.of(
        Integer.parseInt(debtDateSplit[2]),
        Integer.parseInt(debtDateSplit[1]),
        Integer.parseInt(debtDateSplit[0])
    )
    val endDate = LocalDate.of(
        Integer.parseInt(dateSplit[2]),
        Integer.parseInt(dateSplit[1]),
        Integer.parseInt(dateSplit[0])
    )

    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
    return daysBetween.toInt()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDatePriority(debtDate: String): Int {
    val today = LocalDate.now()
    if (debtDate == "null") {
        return Int.MAX_VALUE // Place "Due date not set" at the bottom
    }
    val debtDateSplit = debtDate.split("-")
    val debtLocalDate = LocalDate.of(
        Integer.parseInt(debtDateSplit[2]),
        Integer.parseInt(debtDateSplit[1]),
        Integer.parseInt(debtDateSplit[0])
    )
    return when {
        debtLocalDate.isBefore(today) -> Int.MIN_VALUE // Place passed dates at the top
        debtLocalDate == today -> Int.MIN_VALUE + 1 // Place today's date below passed dates
        else -> -debtLocalDate.until(today, ChronoUnit.DAYS)
            .toInt() // Place future dates at the bottom based on the number of days until today
    }
}
