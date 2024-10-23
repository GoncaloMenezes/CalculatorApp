package pt.com.calculator.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalculationDao {
    @Query("SELECT * FROM Calculation ORDER BY uid DESC")
    fun getAll(): List<Calculation>

    @Insert()
    fun insert(calculation: Calculation)

    @Delete
    fun delete(calculation: Calculation)
}