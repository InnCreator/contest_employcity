package com.inn_creator.contestemploycity.fragments.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inn_creator.contestemploycity.ApiRepository
import com.inn_creator.contestemploycity.db.currency.CurrencyRepositoryImpl
import com.inn_creator.contestemploycity.model.Symbols
import com.inn_creator.contestemploycity.model.ValueCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val repo: CurrencyRepositoryImpl
) : ViewModel() {

    private val _listSymbols = MutableStateFlow<List<Symbols>>(listOf())
    val listSymbols: StateFlow<List<Symbols>> = _listSymbols

    private val _listCurrency = MutableStateFlow<ArrayList<ValueCurrency>>(arrayListOf())
    val listCurrency: StateFlow<ArrayList<ValueCurrency>> = _listCurrency

    private val _visibilityProgress = MutableStateFlow(false)
    val visibilityProgress: StateFlow<Boolean> = _visibilityProgress

    var apiList = listOf<ValueCurrency>()


    init {
        viewModelScope.launch {
            try {
                val list = apiRepository.getListSymbols()
                _listSymbols.emit(list)
            } catch (e : Throwable){
                //TODO add info "check internet connection"
            }

        }
    }

    fun getCurrencies(code: String) {
        viewModelScope.launch {
            _visibilityProgress.emit(true)
            apiList = apiRepository.getLatestCurrencies(code)
            _visibilityProgress.emit(false)
            showResult()
        }

    }

    suspend fun codeToFavorite(currency: ValueCurrency) {
        apiRepository.updateCodeToFavorite(currency)
        showResult()
    }

    private suspend fun showResult() {
        val listCurrency = repo.getAllCurrency()
        val list : ArrayList<ValueCurrency> =
            apiList.map {
                val currency = listCurrency.first { currency -> it.code == currency.code }
                it.copy(isFavorite = currency.isFavorite, description = currency.description
            ) } as ArrayList<ValueCurrency>
        _listCurrency.emit(list)
    }
}
