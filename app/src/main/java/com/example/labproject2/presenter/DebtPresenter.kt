package com.example.labproject2.presenter


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

    override fun addDebt(
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        model.addDebt(name, amount, date, currency, isDebtor, dateInteger)
    }

    override fun updateDebt(
        id: Int,
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        model.updateDebt(id, name, amount, date, currency, isDebtor, dateInteger)
    }

    override fun deleteDebt(
        id: Int,
        name: String,
        amount: String,
        date: String,
        currency: String,
        isDebtor: Boolean,
        dateInteger: Int
    ) {
        model.deleteDebt(id, name, amount, date, currency, isDebtor, dateInteger)
    }
}


