package pt.com.calculator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.com.calculator.data.model.Calculation
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.utils.Calculator

class MainViewModel (
    private val calculationRepository: CalculationRepository,
    private val calculator: Calculator
) : ViewModel() {
    private val _input = MutableLiveData<String>()
    val input: LiveData<String> get() = _input

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    init {
        _input.value = ""
        _result.value = ""
    }

    fun addNumber(number: String) {
        _input.value += "$number"
    }

    fun addDecimalPoint() {
        if(_input.value!!.isEmpty() || lastIsOperator()){
            _input.value += "0"
        }
        _input.value += "."
    }

    fun addOperator(operator: String) {
        if(!lastIsOperator() && _input.value!!.isNotEmpty()){
            _input.value += operator
        }
    }

    fun calculateResult(roundResult: Boolean) {
        if(_input.value!!.isNotEmpty() && !lastIsOperator()){
            _result.value = calculator.evaluateExpression(_input.value!!, roundResult).toString()
            viewModelScope.launch(Dispatchers.IO) {
                calculationRepository.insertCalculation( Calculation(expression = _input.value!!, result = _result.value!!) )
            }
        }
    }

    fun clearAll(){
        _input.value = ""
        _result.value = ""
    }

    fun clearLast(){
        if(_input.value!!.isNotEmpty()){
            _input.value = _input.value!!.dropLast(1)
        }
    }

    private fun  lastIsOperator(): Boolean {
        return _input.value!!.endsWith("+") ||
                _input.value!!.endsWith("-") ||
                _input.value!!.endsWith("×") ||
                _input.value!!.endsWith("÷")
    }
}