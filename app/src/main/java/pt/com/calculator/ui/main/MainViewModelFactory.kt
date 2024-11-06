package pt.com.calculator.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.utils.Calculator

class MainViewModelFactory(
    private val calculationRepository: CalculationRepository,
    private val calculator: Calculator
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(calculationRepository, calculator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}