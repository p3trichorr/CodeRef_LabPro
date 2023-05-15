package com.example.labproject2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class Debts(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val amount: String,
    val date: String,
    val currency: String,
    val isDebtor: Boolean,
    var dateInteger: Int
)
