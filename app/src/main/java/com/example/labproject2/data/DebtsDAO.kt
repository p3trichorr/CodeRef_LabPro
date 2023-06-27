package com.example.labproject2.data

import androidx.room.*

@Dao
interface DebtsDAO {
    @Query("SELECT * FROM debts")
    fun getAllDebts(): List<Debts>

    @Query("SELECT * FROM debts WHERE date = :todayDate OR date = 'null'")
    fun getDebtsDueToday(todayDate: String): List<Debts>

    @Query("SELECT * FROM debts WHERE isDebtor = 0")
    fun getDebtsOwedToUs(): List<Debts>

    @Query("SELECT * FROM debts WHERE isDebtor = 1")
    fun getDebtsWeOwe(): List<Debts>

    @Insert
    fun insertDebts(debtList: Debts): Long

    @Update
    fun updateDebts(debtList: Debts)

    @Delete
    fun deleteDebts(debtList: Debts)

    @Query("DELETE FROM debts")
    fun clearAllDebts()
}