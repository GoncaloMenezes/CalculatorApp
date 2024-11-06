package pt.com.calculator.ui.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.com.calculator.ui.components.HistoryAdapter
import pt.com.calculator.R
import pt.com.calculator.data.model.AppDatabase
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.data.room.CalculationDao
import pt.com.calculator.ui.main.MainActivity

class HistoryActivity : AppCompatActivity() {

    private val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val calculationDao: CalculationDao by lazy { db.calculationDao() }

    private val calculationRepository: CalculationRepository by lazy {
        CalculationRepository(
            calculationDao
        )
    }

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(calculationRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val emptyHistoryTV: TextView = findViewById(R.id.textView_emptyHistory)
        val goBackBtn: Button = findViewById(R.id.button_goBack)

        viewModel.loadHistory()

        viewModel.history.observe(this) { history ->
            if(history.isNotEmpty()){
                val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = HistoryAdapter(history)
                emptyHistoryTV.visibility = View.GONE
            }else{
                emptyHistoryTV.visibility = View.VISIBLE
            }

        }

        goBackBtn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}