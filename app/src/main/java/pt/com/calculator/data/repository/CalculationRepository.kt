package pt.com.calculator.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.com.calculator.data.model.Calculation
import pt.com.calculator.data.room.CalculationDao

class CalculationRepository(private val calculationDao: CalculationDao) {
    suspend fun getAllCalculations(): List<Calculation> {
        return withContext(Dispatchers.IO){
            calculationDao.getAll()
        }
    }

    suspend fun insertCalculation(calculation: Calculation){
        withContext(Dispatchers.IO){
            calculationDao.insert(calculation)
        }
    }

    suspend fun deleteCalculation(calculation: Calculation){
        withContext(Dispatchers.IO){
            calculationDao.delete(calculation)
        }
    }
}