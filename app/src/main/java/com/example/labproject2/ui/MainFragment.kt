package com.example.labproject2.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labproject2.data.DebtDatabase
import com.example.labproject2.data.Debts
import com.example.labproject2.data.PassedData
import com.example.labproject2.databinding.FragmentMainBinding
import com.example.labproject2.getDatePriority
import com.example.labproject2.presenter.DebtModel
import com.example.labproject2.presenter.DebtPresenter

class MainFragment : Fragment(), DebtContract.View {

    private var _binding: FragmentMainBinding? = null
    private lateinit var presenter: DebtContract.Presenter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.mainRecycle.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val debtDao = DebtDatabase.getInstance(requireContext()).debtsDao()
        val model: DebtContract.Model = DebtModel(debtDao)

        presenter = DebtPresenter(this, model)
        presenter.getDebtsDueToday()

        binding.addDebt.setOnClickListener {
            val nextPage = Intent(activity, AddDebtDetails::class.java)
            nextPage.putExtra(PassedData.ADD_SAVE, "add")
            startActivity(nextPage)
        }
    }

    override fun onResume() {
        super.onResume()

        val debtDao = DebtDatabase.getInstance(requireContext()).debtsDao()
        val model: DebtContract.Model = DebtModel(debtDao)

        presenter = DebtPresenter(this, model)
        presenter.getDebtsDueToday()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showAllDebts(debts: List<Debts>) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showDebtsDueToday(debts: List<Debts>) {
        val sortWithDate = debts.sortedBy { getDatePriority(it.date) }
        val adapter = DebtsAdapter(sortWithDate, "MainFragment")
        binding.mainRecycle.adapter = adapter
    }

    override fun showDebtsOwedToUs(debts: List<Debts>) {
        TODO("Not yet implemented")
    }

    override fun showDebtsWeOwe(debts: List<Debts>) {
        TODO("Not yet implemented")
    }
}