package com.example.labproject2.presenter

import com.example.labproject2.data.Debts


class DebtPresenter(
    private val view: DebtContract.View,
    private val model: DebtContract.Model
) : DebtContract.Presenter {

    override fun getAllDebts() {
        model.getAllDebts { debts ->
            view.showAllDebts(debts)
        }
    }

    override fun getDebtsDueToday() {
        model.getDebtsDueToday { debts ->
            view.showDebtsDueToday(debts)
        }
    }

    override fun getDebtsOwedToUs() {
        model.getDebtsOwedToUs { debts ->
            view.showDebtsOwedToUs(debts)
        }
    }

    override fun getDebtsWeOwe() {
        model.getDebtsWeOwe { debts ->
            view.showDebtsWeOwe(debts)
        }
    }

    override fun addDebt(debts: Debts) {
        model.addDebt(debts)
    }

    override fun updateDebt(debts: Debts) {
        model.updateDebt(debts)
    }

    override fun deleteDebt(debts: Debts) {
        model.deleteDebt(debts)
    }
}


