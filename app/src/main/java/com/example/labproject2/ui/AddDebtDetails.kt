package com.example.labproject2.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.labproject2.R
import com.example.labproject2.data.DebtDatabase
import com.example.labproject2.data.Debts
import com.example.labproject2.data.PassedData
import com.example.labproject2.databinding.AddDebtDetailsBinding
import com.example.labproject2.presenter.DebtModel
import com.example.labproject2.presenter.DebtPresenter
import java.util.*

class AddDebtDetails : AppCompatActivity(), DebtContract.View {

    lateinit var binding: AddDebtDetailsBinding
    private lateinit var dateString: String
    private var aDateInteger: Int = 0
    private var btnState: Boolean = false

    var nameField: EditText? = null
    var amountField: EditText? = null

    var isAllFieldsChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddDebtDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        aDateInteger = intent.getIntExtra(PassedData.DATE_INTEGER, 0)
        dateString = intent.getStringExtra(PassedData.DATE_INFO).toString()
        btnState = intent.getBooleanExtra(PassedData.DEBTOR_INFO, false)

        nameField = binding.addCreditorName
        amountField = binding.debtSum

        setInfo()

        val debtDao = DebtDatabase.getInstance(this).debtsDao()
        val model: DebtContract.Model = DebtModel(debtDao)
        val presenter = DebtPresenter(this, model)

        binding.debtorSwitch.setOnCheckedChangeListener { _, isChecked ->
            btnState = isChecked == true
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // to make unpicked date to the bottom of the list
        aDateInteger = ((year * 100000) + ((month + 1) * 100) + day)

        binding.dateButton.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->

                    dateString = if (monthOfYear + 1 < 10) {
                        (dayOfMonth.toString() + "-0" + (monthOfYear + 1) + "-" + year)
                    } else {
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    }
                    //To set list in a ascending manner and to check today date
                    aDateInteger = ((year * 10000) + ((monthOfYear + 1) * 100) + dayOfMonth)
                    binding.returnUntil.text = getString(R.string.return_until, dateString)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        val currIndex = when (intent.getStringExtra(PassedData.CURR_INFO)) {
            "RUB" -> 0
            "USD" -> 1
            "EUR" -> 2
            "IDR" -> 3
            else -> 0
        }

        val spinValue = binding.currencySpinner
        ArrayAdapter.createFromResource(
            this, R.array.spinner_value, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinValue.adapter = adapter
            spinValue.setSelection(currIndex)
        }

        binding.saveBtn.setOnClickListener {
            val debtorName = binding.addCreditorName.text.toString()
            val debtAmount = binding.debtSum.text.toString()
            val debtCurrency = binding.currencySpinner.selectedItem.toString()
            var debtDate = dateString
            val isDebtor = btnState
            val dateInteger = aDateInteger
            isAllFieldsChecked = checkAllFields()
            debtDate = fillDate(debtDate)
            if (isAllFieldsChecked) {
                when (intent.getStringExtra(PassedData.ADD_SAVE)) {
                    "add" -> {
                        presenter.addDebt(
                            debtorName,
                            debtAmount,
                            debtDate,
                            debtCurrency,
                            isDebtor,
                            dateInteger
                        )
                        Toast.makeText(this, getString(R.string.update_toast), Toast.LENGTH_SHORT).show()
                    }
                    "save" -> {
                        val debtId = intent.getIntExtra(PassedData.ID_INFO, 0)
                        presenter.updateDebt(
                            debtId,
                            debtorName,
                            debtAmount,
                            debtDate,
                            debtCurrency,
                            isDebtor,
                            dateInteger
                        )
                        Toast.makeText(this, getString(R.string.update_toast), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                finish()
            }
        }

        binding.deleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this@AddDebtDetails)
            builder.setMessage(getString(R.string.delete_popup))
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val debtorName = binding.addCreditorName.text.toString()
                    val debtAmount = binding.debtSum.text.toString()
                    val debtCurrency = binding.currencySpinner.selectedItem.toString()
                    val debtDate = dateString
                    val isDebtor = btnState
                    val dateInteger = aDateInteger
                    val debtId = intent.getIntExtra(PassedData.ID_INFO, 0)
                    presenter.deleteDebt(
                        debtId,
                        debtorName,
                        debtAmount,
                        debtDate,
                        debtCurrency,
                        isDebtor,
                        dateInteger
                    )
                    Toast.makeText(this, getString(R.string.delete_toast), Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun setInfo() {
        when (intent.getStringExtra(PassedData.ADD_SAVE)) {
            "add" -> {
                supportActionBar?.title = getString(R.string.action_add)
                binding.deleteBtn.visibility = View.INVISIBLE
                binding.saveBtn.text = getString(R.string.save_button)
            }

            "save" -> {
                supportActionBar?.title = getString(R.string.action_details)
                binding.debtorSwitch.isChecked =
                    intent.getBooleanExtra(PassedData.DEBTOR_INFO, false)
                binding.addCreditorName.setText(intent.getStringExtra(PassedData.CRED_INFO))
                binding.returnUntil.text =
                    getString(R.string.return_until, intent.getStringExtra(PassedData.DATE_INFO))
                binding.debtSum.setText(intent.getStringExtra(PassedData.AMOUNT_INFO))
                binding.saveBtn.text = getString(R.string.update_button)
            }
        }
    }

    fun checkAllFields(): Boolean {
        if (nameField!!.length() == 0) {
            nameField!!.error = getString(R.string.name_mt)
            return false
        } else if (amountField!!.length() == 0) {
            amountField!!.error = getString(R.string.amount_mt)
            return false
        }
        return true
    }

    private fun fillDate(debtDate: String): String {
        if (debtDate == "null") {
            return getString(R.string.date_unpicked)
        }
        return debtDate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showAllDebts(debts: List<Debts>) {
        TODO("Not yet implemented")
    }

    override fun showDebtsDueToday(debts: List<Debts>) {
        TODO("Not yet implemented")
    }

    override fun showDebtsOwedToUs(debts: List<Debts>) {
        TODO("Not yet implemented")
    }

    override fun showDebtsWeOwe(debts: List<Debts>) {
        TODO("Not yet implemented")
    }
}
