package com.example.labproject2.flaw

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DuplicateCode {

    @RequiresApi(Build.VERSION_CODES.O)
    val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    @RequiresApi(Build.VERSION_CODES.O)
    fun isDateFormatValid(date: String): Boolean {
        return try {
            LocalDate.parse(date, format)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isDebtAddedValid(
        debtorName: String,
        debtAmount: Int,
        debtCurrency: String,
        debtDate: String,
        debtInteger: Int
    ): Boolean {
        val tempDebtInteger: Int
        try {
            LocalDate.parse(debtDate, format)
            val dateSplit = debtDate.split("-")
            val dayInt = Integer.parseInt(dateSplit[0])
            val monthInt = Integer.parseInt(dateSplit[1])
            val yearInt = Integer.parseInt(dateSplit[2])
            tempDebtInteger = (yearInt * 10000) + (monthInt * 100) + dayInt
        } catch (e: DateTimeParseException) {
            return false
        }

        if (debtInteger != tempDebtInteger) {
            return false
        }

        if (debtorName.isEmpty() || debtAmount <= 0 || debtCurrency.isEmpty()) {
            return false
        }
        return true
    }
}