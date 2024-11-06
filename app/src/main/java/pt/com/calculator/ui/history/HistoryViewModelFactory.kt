package pt.com.calculator.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.com.calculator.data.repository.CalculationRepository

class HistoryViewModelFactory (
    private val calculationRepository: CalculationRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(calculationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}