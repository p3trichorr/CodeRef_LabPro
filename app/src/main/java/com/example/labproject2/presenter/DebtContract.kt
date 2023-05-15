import android.content.Context
import com.example.labproject2.data.Debts

interface DebtContract {
    interface View {
        fun showAllDebts(debts: List<Debts>)
        fun showDebtsDueToday(debts: List<Debts>)
        fun showDebtsOwedToUs(debts: List<Debts>)
        fun showDebtsWeOwe(debts: List<Debts>)
    }

    interface Presenter {
        fun getAllDebts()
        fun getDebtsDueToday()
        fun getDebtsOwedToUs()
        fun getDebtsWeOwe()
        fun addDebt(
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )

        fun updateDebt(
            id: Int,
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )

        fun deleteDebt(
            id: Int,
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )
    }

    interface Model {
        fun getAllDebts(callback: (List<Debts>) -> Unit)
        fun getDebtsDueToday(callback: (List<Debts>) -> Unit)
        fun getDebtsOwedToUs(callback: (List<Debts>) -> Unit)
        fun getDebtsWeOwe(callback: (List<Debts>) -> Unit)
        fun addDebt(
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )

        fun updateDebt(
            id: Int,
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )

        fun deleteDebt(
            id: Int,
            name: String,
            amount: String,
            date: String,
            currency: String,
            isDebtor: Boolean,
            dateInteger: Int
        )
    }
}
