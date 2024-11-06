package pt.com.calculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calculation(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String
)
