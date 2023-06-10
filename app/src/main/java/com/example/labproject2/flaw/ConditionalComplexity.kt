package com.example.labproject2.flaw

class ConditionalComplexity(private val callFunction: DebtFunCall) {

    fun isDebtSavedOrUpdated(
        allFieldChecked: Boolean,
        buttonState: String,
        buttonCommand: String
    ): Boolean {
        if (!allFieldChecked) {
            return false
        }
        if (buttonState.isEmpty()) {
            return false
        }
        return if (buttonCommand == "Add") {
            callFunction.addData()
            true
        } else if (buttonCommand == "Update") {
            callFunction.updateDebt()
            true
        } else {
            false
        }
    }

    interface DebtFunCall {
        fun addData()
        fun updateDebt()
    }
}