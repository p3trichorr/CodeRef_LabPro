package com.example.labproject2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labproject2.data.DebtDatabase
import com.example.labproject2.data.Debts
import com.example.labproject2.databinding.FragmentMyDebtsBinding
import com.example.labproject2.presenter.DebtModel
import com.example.labproject2.presenter.DebtPresenter

class MyDebtsFragment : Fragment(), DebtContract.View {

    private var _binding: FragmentMyDebtsBinding? = null
    private lateinit var presenter: DebtContract.Presenter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDebtsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.myDebtsRecycle.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val debtDao = DebtDatabase.getInstance(requireContext()).debtsDao()
        val model: DebtContract.Model = DebtModel(debtDao)

        presenter = DebtPresenter(this, model)
        presenter.getDebtsWeOwe()
    }

    override fun onResume() {
        super.onResume()

        val debtDao = DebtDatabase.getInstance(requireContext()).debtsDao()
        val model: DebtContract.Model = DebtModel(debtDao)

        presenter = DebtPresenter(this, model)
        presenter.getDebtsWeOwe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val adapter = DebtsAdapter(debts, "MyDebtsFragment")
        binding.myDebtsRecycle.adapter = adapter
    }
}