package com.example.labproject2

import java.text.SimpleDateFormat
import java.util.*

fun getTodayDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}