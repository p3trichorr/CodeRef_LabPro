package com.example.labproject2.presenter

import android.provider.Settings.Global.getString
import com.example.labproject2.R
import com.example.labproject2.data.Debts
import com.example.labproject2.data.DebtsDAO
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class DebtModel(private val debtDao: DebtsDAO) : DebtContract.Model {

    @OptIn(DelicateCoroutinesApi::class)
    override fun getAllDebts(callback: (List<Debts>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val debts = debtDao.getAllDebts()
            withContext(Dispatchers.Main) {
                callback(debts)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getDebtsDueToday(callback: (List<Debts>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val debts = debtDao.getDebtsDueToday(getTodayDate())
            withContext(Dispatchers.Main) {
                callback(debts)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getDebtsOwedToUs(callback: (List<Debts>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val debts = debtDao.getDebtsOwedToUs()
            withContext(Dispatchers.Main) {
                callback(debts)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun getDebtsWeOwe(callback: (List<Debts>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val debts = debtDao.getDebtsWeOwe()
            withContext(Dispatchers.Main) {
                callback(debts)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun addDebt(
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val debt = Debts(
                id = 0,
                name = name,
                amount = amount,
                date = date,
                currency = currency,
                isDebtor = isDebtor,
                dateInteger = dateInteger
            )
            debtDao.insertDebts(debt)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun updateDebt(
        id: Int,
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val debt = Debts(
                id = id,
                name = name,
                amount = amount,
                date = date,
                currency = currency,
                isDebtor = isDebtor,
                dateInteger = dateInteger
            )
            debtDao.updateDebts(debt)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun deleteDebt(
        id: Int,
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val debt = Debts(
                id = id,
                name = name,
                amount = amount,
                date = date,
                currency = currency,
                isDebtor = isDebtor,
                dateInteger = dateInteger
            )
            debtDao.deleteDebts(debt)
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}

