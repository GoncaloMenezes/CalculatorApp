package pt.com.calculator.ui.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pt.com.calculator.ui.components.HistoryAdapter
import pt.com.calculator.data.model.AppDatabase
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.data.room.CalculationDao
import pt.com.calculator.databinding.ActivityHistoryBinding
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

    private lateinit var activityHistoryBinding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHistoryBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(activityHistoryBinding.root)

        viewModel.loadHistory()

        viewModel.history.observe(this) { history ->
            if(history.isNotEmpty()){
                activityHistoryBinding.recyclerView.layoutManager = LinearLayoutManager(this)
                activityHistoryBinding.recyclerView.adapter = HistoryAdapter(history)
                activityHistoryBinding.textViewEmptyHistory.visibility = View.GONE
            }else{
                activityHistoryBinding.textViewEmptyHistory.visibility = View.VISIBLE
            }
        }

        activityHistoryBinding.buttonGoBack.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}