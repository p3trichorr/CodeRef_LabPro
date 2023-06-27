package com.example.labproject2.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private lateinit var binding: AddDebtDetailsBinding
    private lateinit var dateString: String
    private var btnState = false
    private var isAllFieldsChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddDebtDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        dateString = intent.getStringExtra(PassedData.DATE_INFO).toString()
        btnState = intent.getBooleanExtra(PassedData.DEBTOR_INFO, false)

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

        binding.dateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->

                    dateString = if (monthOfYear + 1 < 10) {
                        (dayOfMonth.toString() + "-0" + (monthOfYear + 1) + "-" + year)
                    } else {
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    }
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
            val debts = Debts(
                id = 0,
                binding.addCreditorName.text.toString(),
                binding.debtSum.text.toString(),
                dateString,
                binding.currencySpinner.selectedItem.toString(),
                btnState,
                0
            )
            isAllFieldsChecked = checkAllFields()
            if (isAllFieldsChecked) {
                when (intent.getStringExtra(PassedData.ADD_SAVE)) {
                    "add" -> {
                        presenter.addDebt(debts)
                        Toast.makeText(this, getString(R.string.update_toast), Toast.LENGTH_SHORT)
                            .show()
                    }
                    "save" -> {
                        debts.id = intent.getIntExtra(PassedData.ID_INFO, 0)
                        presenter.updateDebt(debts)
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
                    val debts = Debts(
                        intent.getIntExtra(PassedData.ID_INFO, 0),
                        binding.addCreditorName.text.toString(),
                        binding.debtSum.text.toString(),
                        dateString,
                        binding.currencySpinner.selectedItem.toString(),
                        btnState,
                        0
                    )
                    presenter.deleteDebt(debts)
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

    private fun checkAllFields(): Boolean {
        if (binding.addCreditorName.length() == 0) {
            binding.addCreditorName.error = getString(R.string.name_mt)
            return false
        } else if (binding.debtSum.length() == 0) {
            binding.debtSum.error = getString(R.string.amount_mt)
            return false
        }
        return true
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
