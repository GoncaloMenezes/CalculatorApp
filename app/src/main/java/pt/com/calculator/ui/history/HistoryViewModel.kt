package pt.com.calculator.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.com.calculator.data.model.Calculation
import pt.com.calculator.data.repository.CalculationRepository

class HistoryViewModel(
    private val calculationRepository: CalculationRepository
) : ViewModel() {
    private val _history = MutableLiveData<List<Calculation>>()
    val history: LiveData<List<Calculation>> get() = _history

    init {
        _history.value = emptyList()
    }

    fun loadHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            // Maybe i need to change to postValue
            _history.postValue(calculationRepository.getAllCalculations())
        }
    }
}