package com.example.labproject2.ui

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.labproject2.R
import com.example.labproject2.data.Debts
import com.example.labproject2.data.PassedData
import com.example.labproject2.databinding.RecyclerItemBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

class DebtsAdapter(
    private var debtList: List<Debts>,
    private val fragmentId: String
) : RecyclerView.Adapter<DebtsAdapter.DebtListViewHolder>() {

    class DebtListViewHolder(binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val untilDate: TextView = binding.untilDate
        val debtAmount: TextView = binding.debtAmount
        val debtCurrency: TextView = binding.debtCurrency
        val peopleName: TextView = binding.peopleName
        val debtCard: CardView = binding.debtCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtListViewHolder {
        val binding =
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DebtListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DebtListViewHolder, position: Int) {
        if (debtList[position].date == "Due date not set") {
            holder.untilDate.text =
                holder.itemView.context.getString(R.string.date_unpicked)
        } else {
            holder.untilDate.text =
                holder.itemView.context.getString(R.string.until_date, debtList[position].date)
        }
        holder.debtAmount.text = debtList[position].amount
        holder.debtCurrency.text = debtList[position].currency
        holder.peopleName.text = debtList[position].name
        //if a debt is due today
        if (debtList[position].dateInteger == TodayDateInt) {
            if (fragmentId == "MyDebtsFragment" || fragmentId == "SomeoneOwesMeFragment") {
                holder.debtCard.background = holder.itemView.context.getDrawable(R.color.button)
                holder.untilDate.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.peopleName.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.debtAmount.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.debtCurrency.setTextColor(holder.itemView.context.getColor(R.color.white))
            }
            holder.untilDate.text = holder.itemView.context.getString(R.string.due_today, "Today!")
            //if a debt date is picked and the due is past
        } else if (debtList[position].dateInteger < TodayDateInt) {
            if (fragmentId == "MyDebtsFragment" || fragmentId == "SomeoneOwesMeFragment") {
                holder.debtCard.background = holder.itemView.context.getDrawable(R.color.tertiary)
                holder.untilDate.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.peopleName.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.debtAmount.setTextColor(holder.itemView.context.getColor(R.color.white))
                holder.debtCurrency.setTextColor(holder.itemView.context.getColor(R.color.white))
            }
            val datePassed = getDatePassed(debtList[position].date)
            holder.untilDate.text =
                holder.itemView.context.getString(R.string.past_due, datePassed.toString())
        }

        holder.itemView.setOnClickListener {
            val nextPage = Intent(holder.itemView.context, AddDebtDetails::class.java).apply {
                putExtra(PassedData.ADD_SAVE, "save")
                putExtra(PassedData.CRED_INFO, debtList[position].name)
                putExtra(PassedData.DATE_INFO, debtList[position].date)
                putExtra(PassedData.AMOUNT_INFO, debtList[position].amount)
                putExtra(PassedData.CURR_INFO, debtList[position].currency)
                putExtra(PassedData.DEBTOR_INFO, debtList[position].isDebtor)
                putExtra(PassedData.DATE_INTEGER, debtList[position].dateInteger)
                putExtra(PassedData.ID_INFO, debtList[position].id)
            }
            holder.itemView.context.startActivity(nextPage)
        }
    }

    override fun getItemCount(): Int {
        return debtList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDatePassed(debtDate: String): Int {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = dateFormat.format(Date())
        val dateSplit = dateString.split("-")
        val debtDateSplit = debtDate.split("-")
        val startDate = LocalDate.of(
            Integer.parseInt(debtDateSplit[2]),
            Integer.parseInt(debtDateSplit[1]),
            Integer.parseInt(debtDateSplit[0])
        )
        val endDate = LocalDate.of(
            Integer.parseInt(dateSplit[2]),
            Integer.parseInt(dateSplit[1]),
            Integer.parseInt(dateSplit[0])
        )

        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        return daysBetween.toInt()
    }

    companion object {
        val TodayDateInt: Int = getTodayDateInteger()
        private fun getTodayDateInteger(): Int {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val dateString = dateFormat.format(Date())
            val dateSplit = dateString.split("-")
            val dayInt = Integer.parseInt(dateSplit[0])
            val monthInt = Integer.parseInt(dateSplit[1])
            val yearInt = Integer.parseInt(dateSplit[2])
            return ((yearInt * 10000) + (monthInt * 100) + dayInt)
        }
    }
}