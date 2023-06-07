package com.example.labproject2.flaw

import android.content.Context
import android.util.Log
import com.example.labproject2.data.DebtDatabase
import com.example.labproject2.presenter.DebtModel
import com.example.labproject2.presenter.DebtPresenter

object ConditionalComplexity {

    fun isDebtSavedOrUpdated(
        allFieldChecked: Boolean,
        addOrUpdateState: String,
        passDebtID: Boolean
    ): Boolean {
        val call = DebtData()
        if (!allFieldChecked) {
            return false
        }
        if (addOrUpdateState == "Add") {
            return if (passDebtID) {
                false
            } else {
                call.addData()
            }
        } else if (addOrUpdateState == "Update") {
            return if (passDebtID) {
                call.updateDebt()
            } else {
                false
            }
        } else {
            return false
        }
    }

    class DebtData {
        fun addData(): Boolean {
            return true
        }

        fun updateDebt(): Boolean {
            return true
        }
    }
}