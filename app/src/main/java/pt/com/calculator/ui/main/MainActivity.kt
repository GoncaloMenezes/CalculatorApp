package pt.com.calculator.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView
import pt.com.calculator.utils.Calculator
import pt.com.calculator.data.model.AppDatabase
import pt.com.calculator.data.repository.CalculationRepository
import pt.com.calculator.data.room.CalculationDao
import androidx.activity.viewModels
import pt.com.calculator.databinding.ActivityMainBinding
import pt.com.calculator.ui.history.HistoryActivity

class MainActivity : AppCompatActivity() {
    private val db: AppDatabase by lazy { AppDatabase.getInstance(this) }
    private val calculationDao: CalculationDao by lazy { db.calculationDao() }

    private val calculationRepository: CalculationRepository by lazy {
        CalculationRepository(
            calculationDao
        )
    }
    private val calculator: Calculator by lazy { Calculator() }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(calculationRepository, calculator)
    }

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setupClearBtns()
        setupOperatorBtns()
        setupNumberBtns()
        setupDecimalBtn()
        setupEqualsBtns()

        activityMainBinding.buttonHistory.setOnClickListener {
            val intent: Intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        viewModel.input.observe(this) { input ->
            activityMainBinding.textViewInput.text = input
        }

        viewModel.result.observe(this) { result ->
            activityMainBinding.textViewResult.text = result
        }
    }

    private fun setupDecimalBtn() {
        activityMainBinding.buttonDecimalPoint.setOnClickListener {
            viewModel.addDecimalPoint()
            handleHorizontalScroll(activityMainBinding.textViewInput, activityMainBinding.horizontalScrollViewInput)
        }
    }
    private fun setupEqualsBtns() {
        activityMainBinding.buttonEquals.setOnClickListener {
            viewModel.calculateResult(false)
            handleHorizontalScroll(activityMainBinding.textViewResult, activityMainBinding.horizontalScrollViewResult)
        }
        activityMainBinding.buttonRound.setOnClickListener {
            viewModel.calculateResult(true)
            handleHorizontalScroll(activityMainBinding.textViewResult, activityMainBinding.horizontalScrollViewResult)
        }
    }

    private fun setupClearBtns() {
        activityMainBinding.buttonClearAll.setOnClickListener {
            viewModel.clearAll()
        }
        activityMainBinding.buttonClearLast.setOnClickListener {
            viewModel.clearLast()
        }
    }

    private fun setupNumberBtns() {
        val numberButtons = listOf(
            activityMainBinding.button0, activityMainBinding.button1, activityMainBinding.button2, activityMainBinding.button3,
            activityMainBinding.button4, activityMainBinding.button5, activityMainBinding.button6, activityMainBinding.button7,
            activityMainBinding.button8, activityMainBinding.button9
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                viewModel.addNumber(button.text.toString())
                handleHorizontalScroll(activityMainBinding.textViewInput, activityMainBinding.horizontalScrollViewInput)
            }

        }
    }

    private fun setupOperatorBtns() {
        val operatorButtons = listOf(
            activityMainBinding.buttonMultiply, activityMainBinding.buttonDivide,
            activityMainBinding.buttonAddition, activityMainBinding.buttonSubtract
        )

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                viewModel.addOperator(button.text.toString())
                handleHorizontalScroll(activityMainBinding.textViewInput, activityMainBinding.horizontalScrollViewInput)
            }

        }
    }

    private fun handleHorizontalScroll(textView: TextView, scrollView: HorizontalScrollView) {
        textView.post {
            val contentWidth = textView.width
            val scrollViewWidth = scrollView.width

            if (contentWidth > scrollViewWidth) {
                scrollView.post {
                    scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }
            }
        }
    }
}