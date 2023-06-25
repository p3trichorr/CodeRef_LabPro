package com.example.labproject2.presenter

import com.example.labproject2.data.Debts
import com.example.labproject2.data.DebtsDAO
import com.example.labproject2.getTodayDate
import kotlinx.coroutines.*
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
    override fun addDebt(debts: Debts) {
        GlobalScope.launch(Dispatchers.IO) {
            debtDao.insertDebts(debts)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun updateDebt(debts: Debts) {
        GlobalScope.launch(Dispatchers.IO) {
            debtDao.updateDebts(debts)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun deleteDebt(debts: Debts) {
        GlobalScope.launch(Dispatchers.IO) {
            debtDao.deleteDebts(debts)
        }
    }
}

