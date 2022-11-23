package com.inn_creator.contestemploycity.fragments.currencies

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.inn_creator.contestemploycity.BR
import com.inn_creator.contestemploycity.R
import com.inn_creator.contestemploycity.databinding.FragmentMainBinding
import com.inn_creator.contestemploycity.view.SortDialogView
import com.inn_creator.contestemploycity.model.ValueCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        binding.lifecycleOwner = this
        binding.setVariable(BR.vm, viewModel)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listSymbols.collect { symbols ->
                    val mAutoCompleteAdapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        symbols.map { it.code + " " + it.name }
                    )
                    binding.spinner.adapter = mAutoCompleteAdapter
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listCurrency.collect { it ->
                    val list = arrayListOf<ValueCurrency>().apply { addAll(it) }
                    val adapter = CurrenciesAdapter(list) {
                        val item = list[it]
                        launch {
                            viewModel.codeToFavorite(item)
                        }
                    }
                    binding.recyclerView.adapter = adapter
                }
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val currency = viewModel.listSymbols.value[position]
                viewModel.getCurrencies(currency.code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.navigation.setOnItemSelectedListener {
            val isFavorite = it.itemId == R.id.action_favorite
            (binding.recyclerView.adapter as CurrenciesAdapter).hasFavorite(viewModel.listCurrency.value,
                isFavorite)
            return@setOnItemSelectedListener true
        }

        binding.sortBtn.setOnClickListener {
            val dialog = SortDialogView(requireContext()) { bySort, typeSort ->
                (binding.recyclerView.adapter as CurrenciesAdapter).sort(bySort, typeSort)
            }
            dialog.show()
        }
    }

}